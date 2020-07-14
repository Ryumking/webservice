package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ControllerHelper<T> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> aClass;

    public ControllerHelper(Class<T> aClass) {
        this.aClass = aClass;
    }

    public Long getIdFromPath(HttpServletRequest request) {
        final String path = request.getPathInfo();
        String[] urlParams = path.split("/");
        if (urlParams.length == 0) {
            return 0L;
        }
        return Long.parseLong(urlParams[1]);
    }

    public void writeToJson(HttpServletResponse resp, Object obj) {
        try {
            String json = objectMapper.writeValueAsString(obj);
            PrintWriter writer = resp.getWriter();
            resp.setContentType("application/json");
            writer.print(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @SneakyThrows
    public T writeToObject(HttpServletRequest req) {
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            stringBuilder.append(s);
        }
        return objectMapper.readValue(stringBuilder.toString(), aClass);
    }
}

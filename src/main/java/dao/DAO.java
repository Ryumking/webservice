package dao;

import java.util.List;

public interface DAO<Entity, Key> {
    boolean create (Entity entity);
    Entity readById(Key key);
    List<Entity> readAll();
    boolean update(Entity entity);
    boolean delete(Key key);


}

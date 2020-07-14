package dao;

import java.util.List;

public interface DAO<Entity, Key> {
    long create (Entity entity);
    boolean update(Entity entity, Key key);
    boolean delete(Key key);
    Entity readById(Key key);
    List<Entity> readAll();


}

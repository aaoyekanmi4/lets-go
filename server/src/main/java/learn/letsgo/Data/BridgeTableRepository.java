package learn.letsgo.Data;

public interface BridgeTableRepository<T> {
    T findById(int id);
}

package learn.letsgo.Domain;

import learn.letsgo.Data.SavedEventRepository;
import learn.letsgo.Models.SavedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedEventService {
    private final SavedEventRepository repository;

    public SavedEventService(SavedEventRepository repository) {
        this.repository = repository;
    }

    public List<SavedEvent> findAll(int appUserId) {
        return repository.findAll(appUserId);
    }

    public SavedEvent findById(int savedEventId) {
        return repository.findById(savedEventId);
    }
}

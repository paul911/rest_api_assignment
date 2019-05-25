package Demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    @Autowired
    Repo repo;

    @GetMapping("/test")
    public List<TestItem> index() {
        return repo.findAll();
    }

    @GetMapping("/test/{id}")
    public TestItem show(@PathVariable String id) {
        int item = Integer.parseInt(id);
        return repo.findById(item).orElse(null);
    }

    @PostMapping("/test/search")
    public List<TestItem> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return repo.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @PostMapping("/test")
    public TestItem create(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        String content = body.get("content");
        return repo.save(new TestItem(title, content));
    }

    @PutMapping("/test/{id}")
    public TestItem update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int itemID = Integer.parseInt(id);
        TestItem item = repo.findById(itemID).orElse(null);
        assert item != null;
        item.setTitle(body.get("title"));
        item.setContent(body.get("content"));
        return repo.save(item);
    }

    @DeleteMapping("test/{id}")
    public boolean delete(@PathVariable String id) {
        int itemID = Integer.parseInt(id);
        repo.deleteById(itemID);
        return true;
    }
}
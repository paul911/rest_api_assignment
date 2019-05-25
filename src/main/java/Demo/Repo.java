package Demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repo extends JpaRepository<TestItem, Integer> {

    List<TestItem> findByTitleContainingOrContentContaining(String text, String textAgain);

}

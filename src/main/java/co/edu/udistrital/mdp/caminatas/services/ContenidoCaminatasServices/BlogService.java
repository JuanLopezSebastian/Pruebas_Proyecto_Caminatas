package co.edu.udistrital.mdp.caminatas.services.ContenidoCaminatasServices;

import co.edu.udistrital.mdp.caminatas.entities.ContenidoCaminatasEntities.BlogEntity;
import co.edu.udistrital.mdp.caminatas.repositories.ContenidoCaminatasRepositories.I_BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private I_BlogRepository blogRepository;

    public List<BlogEntity> findAll() {
        return blogRepository.findAll();
    }

    public Optional<BlogEntity> findById(Long id) {
        return blogRepository.findById(id);
    }

    public BlogEntity save(BlogEntity blog) {
        return blogRepository.save(blog);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}

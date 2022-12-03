package pl.opinion_collector.backend.database_communication.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.opinion_collector.backend.database_communication.model.Category;
import pl.opinion_collector.backend.database_communication.repository.CategoryRepository;

import java.util.List;

@Component
public class CategoryDatabaseCommunication {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByCategoryName(categoryName);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(String categoryName, Boolean visible) {
        Category category = new Category(categoryName, visible);
        return categoryRepository.save(category);
    }

    public Category updateCategory(String categoryName, Boolean visible) {
        return createCategory(categoryName, visible);
    }

    public Category removeCategory(String categoryName) {
        return categoryRepository.deleteByCategoryName(categoryName);
    }


}

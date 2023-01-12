package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.dto.CategoryDto;
import com.med.gestiondestock.exceptions.EntityNotFoundException;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.exceptions.InvalidOperationException;
import com.med.gestiondestock.model.Article;
import com.med.gestiondestock.model.Category;
import com.med.gestiondestock.repositories.ArticleRepository;
import com.med.gestiondestock.repositories.CategoryRepository;
import com.med.gestiondestock.services.CategoryService;
import com.med.gestiondestock.validator.CategoryValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository){
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        List<String> errors = CategoryValidator.validate(categoryDto);
        if(!errors.isEmpty()){
            log.error("Category not valid {}", categoryDto);
            throw new InvalidEntityException("La Catégorie n'est pas valid", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        Category category = categoryRepository.save(CategoryDto.toCategory(categoryDto));
        CategoryDto dtoResult = CategoryDto.fromCategory(category);
        return dtoResult;
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id == null){
            log.error("Id Catégorie is null");
            return null;
        }
        // TODO 1er methode pour retourné CatgeoryDto

        /*Optional<Category> category = categoryRepository.findById(id);
        CategoryDto dto = CategoryDto.fromCategory(category.get());
        return Optional.of(dto).orElseThrow(()->
                new EntityNotFoundException("aucune categorie a l'id "+id, ErrorCodes.CATEGORY_NOT_FOUND));
        */
        // TODO 2éme methode pour retourné CatgeoryDto

        return categoryRepository.findById(id)
                .map(CategoryDto::fromCategory).orElseThrow(
                        ()-> new EntityNotFoundException("aucune categorie a l'id "+id, ErrorCodes.CATEGORY_NOT_FOUND)
                );


    }

    @Override
    public CategoryDto findByCodeCategory(String codeCategory) {
        if(!StringUtils.hasLength(codeCategory)){
            log.error("code Catégorie is null");
            return null;
        }
        Optional<Category> category = categoryRepository.findByCodeCategory(codeCategory);

            return Optional.of(CategoryDto.fromCategory(category.get())).orElseThrow(()->
                    new EntityNotFoundException("aucune catégorie n'a ce code "+codeCategory, ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromCategory)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Id Catégorie is null");
        }

        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if(!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une catégorie déja contient des articles", ErrorCodes.CATEGORY_ALREADY_USE);
        }

        categoryRepository.deleteById(id);
    }
}

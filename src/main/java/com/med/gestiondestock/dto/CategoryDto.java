package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data @Builder
public class CategoryDto {
    private Integer id;
    private String codeCategory;
    private String designation;
    private Integer idEntreprise;
    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromCategory(Category category){
        if (category == null) {
            throw  new RuntimeException("Category not found!");
        }
        // Category ----> CategoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .codeCategory(category.getCodeCategory())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();

    }

    public static Category toCategory(CategoryDto categoryDto){
        if (categoryDto == null) {
            throw  new RuntimeException("Category not found!");
        }

        //  CategoryDto ----> Category
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCodeCategory(categoryDto.getCodeCategory());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());
        return category;
    }
}

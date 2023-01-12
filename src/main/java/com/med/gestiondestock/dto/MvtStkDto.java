package com.med.gestiondestock.dto;

import com.med.gestiondestock.model.MvtStk;
import com.med.gestiondestock.model.SourceMvtStk;
import com.med.gestiondestock.model.TypeMvt;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data @Builder
public class MvtStkDto {
    private Integer id;
    private Instant dateMvt;
    private BigDecimal quantite;

    private Integer idEntreprise;
    private ArticleDto article;
    private SourceMvtStk sourceMvtStk;
    private TypeMvt typeMvt;

    public static MvtStkDto fromMvtStk(MvtStk mvtStk){
        if(mvtStk == null ) throw new RuntimeException("MvtStk not found!!!");

        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .article(ArticleDto.fromArticle(mvtStk.getArticle()))
                .typeMvt(mvtStk.getTypeMvt())
                .idEntreprise(mvtStk.getIdEntreprise())
                .sourceMvtStk(mvtStk.getSourceMvtStk())
                .build();
    }

    public static  MvtStk toMvtStk(MvtStkDto mvtStkDto){
        if(mvtStkDto == null ) throw new RuntimeException("MvtStk not found!!!");

        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(mvtStkDto.getId());
        mvtStk.setDateMvt(mvtStkDto.getDateMvt());
        mvtStk.setQuantite(mvtStkDto.getQuantite());
        mvtStk.setArticle(ArticleDto.toArticle(mvtStkDto.getArticle()));
        mvtStk.setTypeMvt(mvtStkDto.getTypeMvt());
        mvtStk.setSourceMvtStk(mvtStkDto.getSourceMvtStk());
        mvtStk.setIdEntreprise(mvtStkDto.getIdEntreprise());
        return mvtStk;
    }
}

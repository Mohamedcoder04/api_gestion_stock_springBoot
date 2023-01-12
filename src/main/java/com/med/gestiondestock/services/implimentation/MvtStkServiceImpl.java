package com.med.gestiondestock.services.implimentation;

import com.med.gestiondestock.dto.MvtStkDto;
import com.med.gestiondestock.exceptions.ErrorCodes;
import com.med.gestiondestock.exceptions.InvalidEntityException;
import com.med.gestiondestock.model.TypeMvt;
import com.med.gestiondestock.repositories.MvtStkRepository;
import com.med.gestiondestock.services.ArticleService;
import com.med.gestiondestock.services.MvtStkService;
import com.med.gestiondestock.validator.MvtStkValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MvtStkServiceImpl implements MvtStkService {
    private MvtStkRepository mvtStkRepository;
    private ArticleService articleService;

    @Autowired
    public MvtStkServiceImpl(MvtStkRepository mvtStkRepository, ArticleService articleService) {
        this.mvtStkRepository = mvtStkRepository;
        this.articleService = articleService;
    }

    @Override
    public BigDecimal stockArticle(Integer idArticle) {
        articleService.findById(idArticle);
        return mvtStkRepository.stockReelArticle(idArticle);
    }

    @Override
    public List<MvtStkDto> listeMvtStkArticle(Integer idArticle) {
        articleService.findById(idArticle);
        return mvtStkRepository.findAllByArticleId(idArticle).stream()
                .map(MvtStkDto::fromMvtStk)
                .collect(Collectors.toList());
    }

    @Override
    public MvtStkDto entreeStock(MvtStkDto dto) {
        return entreePositive(dto, TypeMvt.ENTREE);
    }

    @Override
    public MvtStkDto sortieStock(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvt.SORTIE);
    }

    @Override
    public MvtStkDto correctionStockPositif(MvtStkDto dto) {
        return entreePositive(dto, TypeMvt.CORRECTION_POS);
    }

    @Override
    public MvtStkDto correctionStockNegatif(MvtStkDto dto) {
        return sortieNegative(dto, TypeMvt.CORRECTION_NEG);
    }

    private MvtStkDto entreePositive(MvtStkDto dto, TypeMvt typeMvt) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("mouvement stock n'est pas valid {}", dto);
            throw new InvalidEntityException("", ErrorCodes.MVTSTK_NOT_VALID, errors);
        }
        dto.setTypeMvt(typeMvt);
        dto.setQuantite(BigDecimal.valueOf(
                Math.abs(
                        dto.getQuantite().doubleValue()
                )
        ));
        return MvtStkDto.fromMvtStk(
                mvtStkRepository.save(MvtStkDto.toMvtStk(
                        dto
                ))
        );

    }

    private MvtStkDto sortieNegative(MvtStkDto dto, TypeMvt typeMvt) {
        List<String> errors = MvtStkValidator.validate(dto);
        if (!errors.isEmpty()) {
            log.error("mouvement stock n'est pas valid {}", dto);
            throw new InvalidEntityException("", ErrorCodes.MVTSTK_NOT_VALID, errors);
        }
        dto.setTypeMvt(typeMvt);
        dto.setQuantite(BigDecimal.valueOf(
                Math.abs(
                        dto.getQuantite().doubleValue()
                ) * -1
        ));
        return MvtStkDto.fromMvtStk(
                mvtStkRepository.save(MvtStkDto.toMvtStk(
                        dto
                ))
        );

    }
}

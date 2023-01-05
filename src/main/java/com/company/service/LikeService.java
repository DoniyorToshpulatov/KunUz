package com.company.service;

import com.company.dto.LikeDTO;
import com.company.entity.LikeEntity;
import com.company.enums.ProfileRole;
import com.company.exp.AppForbiddenException;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.ArticleLikeDislikeMapper;
import com.company.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    public LikeDTO create(LikeDTO dto, Integer pId){
        Optional<LikeEntity> oldLikeOptional = likeRepository.findByArticleIdAndProfileId(dto.getArticleId(), pId);
        if (oldLikeOptional.isPresent()){
            oldLikeOptional.get().setStatus(dto.getStatus());
            likeRepository.save(oldLikeOptional.get());
            return dto;
        }
        LikeEntity entity = new LikeEntity();
        entity.setProfileId(pId);
        entity.setStatus(dto.getStatus());
        entity.setArticleId(dto.getArticleId());
        likeRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    /*public String update(Integer likeId, LikeDTO dto, Integer pId){
        LikeEntity entity = get(likeId);
        if (!entity.getProfileId().equals(pId)){
            throw new AppForbiddenException("Not Access");
        }
        entity.setStatus(dto.getStatus());
        entity.setUpdatedDate(LocalDateTime.now());
        likeRepository.save(entity);
        return "Success";
    }*/

    public String delete(Integer likeId, ProfileRole role, Integer pId){
        LikeEntity entity = get(likeId);
        if (entity.getProfileId().equals(pId) || role.equals(ProfileRole.ADMIN)){
            likeRepository.delete(entity);
            return "Success";
        }
        throw new AppForbiddenException("Not Access");
    }
    public PageImpl<LikeDTO> listByArticleId(Integer articleId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<LikeEntity> pageList = likeRepository.findAllByArticleId(articleId, pageable);

        List<LikeDTO> likeDTOList = new LinkedList<>();
        for (LikeEntity entity : pageList.getContent()) {
            likeDTOList.add(toDTO(entity));
        }
        return new PageImpl<LikeDTO>(likeDTOList, pageable, pageList.getTotalElements());
    }

    public PageImpl<LikeDTO> listByProfileId(Integer profileId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<LikeEntity> pageList = likeRepository.findAllByProfileId(profileId, pageable);

        List<LikeDTO> likeDTOList = new LinkedList<>();
        for (LikeEntity entity : pageList.getContent()) {
            likeDTOList.add(toDTO(entity));
        }
        return new PageImpl<LikeDTO>(likeDTOList, pageable, pageList.getTotalElements());
    }

    public PageImpl<LikeDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdDate"));

        Page<LikeEntity> pageList = likeRepository.findAll(pageable);

        List<LikeDTO> likeDTOList = new LinkedList<>();
        for (LikeEntity entity : pageList.getContent()) {
            likeDTOList.add(toDTO(entity));
        }
        return new PageImpl<LikeDTO>(likeDTOList, pageable, pageList.getTotalElements());
    }

    public LikeEntity get(Integer likeId){
        return likeRepository.findById(likeId).orElseThrow(() -> new ItemNotFoundException("Like Not Found"));
    }



    public LikeDTO toDTO(LikeEntity entity) {
        LikeDTO dto = new LikeDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setProfileId(entity.getProfileId());
        dto.setArticleId(entity.getArticleId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public LikeDTO getByArticleId(Integer articleId, Integer pId) {
        Optional<LikeEntity> optional = likeRepository.findByArticleIdAndProfileId(articleId, pId);
        if (optional.isPresent()){
            return toDTO(optional.get());
        }
        return new LikeDTO();
    }

    public LikeDTO getCountByArticleId(Integer articleId){
        ArticleLikeDislikeMapper mapper = likeRepository.countArticleLikeAndDislike(articleId);
        LikeDTO dto = new LikeDTO();
        dto.setLikeCount(mapper.getLike_count());
        dto.setDisLikeCount(mapper.getDislike_count());
        return dto;
    }
}

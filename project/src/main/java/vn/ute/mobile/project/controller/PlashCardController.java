package vn.ute.mobile.project.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ResponseListDto;
import vn.ute.mobile.project.dto.plashcard.PlashCardDto;
import vn.ute.mobile.project.exception.BabRequestException;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.flashcard.CreatePlashCardForm;
import vn.ute.mobile.project.form.flashcard.UpdatePlashCardForm;
import vn.ute.mobile.project.mapper.PlashCardMapper;
import vn.ute.mobile.project.model.CustomUserPrincipal;
import vn.ute.mobile.project.model.Deck;
import vn.ute.mobile.project.model.PlashCard;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.criteria.PlashCardCriteria;
import vn.ute.mobile.project.repository.DeckRepository;
import vn.ute.mobile.project.repository.PlashCardRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/plash-card")
public class PlashCardController {
  @Autowired
  private PlashCardRepository plashCardRepository;
  @Autowired
  private PlashCardMapper plashCardMapper;
  @Autowired
  private DeckRepository deckRepository;
  @Autowired
  private UserRepository userRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreatePlashCardForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    User user = userRepository.findById(principal.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
    Deck deck = deckRepository.findByIdAndUserId(request.getDeckId(), user.getId()).orElseThrow(() -> new NotFoundException("Deck not found"));
    if (plashCardRepository.countByDeckId(deck.getId()) >= 20){
      throw new BabRequestException("The number of flashcards in the deck has reached the maximum");
    }
    PlashCard plashCard = plashCardMapper.fromCreatePlashCardToEntity(request);
    plashCard.setUser(user);
    plashCard.setDeck(deck);
    plashCardRepository.save(plashCard);
    apiMessageDto.setMessage("Create plash card successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<ResponseListDto<List<PlashCardDto>>> getListPlashCard(
      @RequestParam(required = true) Long deckId,
      PlashCardCriteria request,
      Pageable pageable){
    ApiMessageDto<ResponseListDto<List<PlashCardDto>>> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    Deck deck = deckRepository.findByIdAndUserId(deckId, principal.getUserId())
        .orElseThrow(() -> new NotFoundException("Deck not found or does not belong to user"));
    request.setUserId(principal.getUserId());
    request.setDeckId(deck.getId());
    Page<PlashCard> plashCards = plashCardRepository.findAll(request.getSpecification(), pageable);
    List<PlashCardDto> list = plashCardMapper.fromEntityToPlashCardDtoList(plashCards.getContent());
    ResponseListDto<List<PlashCardDto>> respone = new ResponseListDto<>(list, plashCards.getTotalElements(), plashCards.getTotalPages());
    apiMessageDto.setData(respone);
    apiMessageDto.setMessage("Get list plash card successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<PlashCardDto> getPlashCard(@PathVariable Long id, @RequestParam(required = true) Long deckId){
    ApiMessageDto<PlashCardDto> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    PlashCard plashCard = plashCardRepository.findByIdAndUserIdAndDeckId(id, principal.getUserId(), deckId)
        .orElseThrow(() -> new NotFoundException("PlashCard not found"));

    PlashCardDto plashCardDto = plashCardMapper.fromEntityToPlashCardDto(plashCard);
    apiMessageDto.setData(plashCardDto);
    apiMessageDto.setMessage("Get plash card successfully");
    return apiMessageDto;
  }

  @PutMapping("/update")
  public ApiMessageDto<String> udpate(@RequestBody @Valid UpdatePlashCardForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    PlashCard plashCard = plashCardRepository.findByIdAndUserIdAndDeckId(request.getId(), principal.getUserId(), request.getDeckId())
        .orElseThrow(() -> new NotFoundException("PlashCard not found"));
    plashCardMapper.updatePlashCardToEntity(request, plashCard);
    plashCardRepository.save(plashCard);
    apiMessageDto.setMessage("Update plash card successfully");
    return apiMessageDto;
  }

  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable Long id, @RequestParam(required = true) Long deckId){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    PlashCard plashCard = plashCardRepository.findByIdAndUserIdAndDeckId(id, principal.getUserId(), deckId)
        .orElseThrow(() -> new NotFoundException("PlashCard not found"));
    plashCardRepository.delete(plashCard);
    apiMessageDto.setMessage("Delete plash card successfully");
    return apiMessageDto;
  }
}

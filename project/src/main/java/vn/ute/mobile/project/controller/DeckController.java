package vn.ute.mobile.project.controller;

import jakarta.transaction.Transactional;
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
import org.springframework.web.bind.annotation.RestController;
import vn.ute.mobile.project.dto.ApiMessageDto;
import vn.ute.mobile.project.dto.ResponseListDto;
import vn.ute.mobile.project.dto.deck.DeckDto;
import vn.ute.mobile.project.exception.NotFoundException;
import vn.ute.mobile.project.form.deck.CreateDeckForm;
import vn.ute.mobile.project.form.deck.UpdateDeckForm;
import vn.ute.mobile.project.mapper.DeckMapper;
import vn.ute.mobile.project.model.CustomUserPrincipal;
import vn.ute.mobile.project.model.Deck;
import vn.ute.mobile.project.model.User;
import vn.ute.mobile.project.model.criteria.DeckCriteria;
import vn.ute.mobile.project.repository.DeckRepository;
import vn.ute.mobile.project.repository.PlashCardRepository;
import vn.ute.mobile.project.repository.UserRepository;

@RestController
@RequestMapping("/v1/api/deck")
public class DeckController {
  @Autowired
  private DeckRepository deckRepository;

  @Autowired
  private DeckMapper deckMapper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PlashCardRepository plashCardRepository;

  @PostMapping("/create")
  public ApiMessageDto<String> create(@RequestBody @Valid CreateDeckForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    // Lấy Authentication từ SecurityContext
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }

    // Lấy userId từ principal
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    Long userId = principal.getUserId();

    // Tìm user từ database
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Deck deck = deckMapper.fromCreateDeckToEntity(request);
    deck.setUser(user);

    deckRepository.save(deck);
    apiMessageDto.setMessage("Create deck successfully");
    return apiMessageDto;
  }

  @GetMapping("/list")
  public ApiMessageDto<ResponseListDto<List<DeckDto>>> getList(DeckCriteria request, Pageable pageable){
    ApiMessageDto<ResponseListDto<List<DeckDto>>> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    request.setUserId(principal.getUserId());
    Page<Deck> decks = deckRepository.findAll(request.getSpecification(), pageable);
    List<DeckDto> list = deckMapper.fromDeckToDtoList(decks.getContent());
    ResponseListDto<List<DeckDto>> response = new ResponseListDto<>(list, decks.getTotalElements(), decks.getTotalPages());
    apiMessageDto.setData(response);
    apiMessageDto.setMessage("Get list deck successfully");
    return apiMessageDto;
  }

  @GetMapping("/get/{id}")
  public ApiMessageDto<DeckDto> getDeck(@PathVariable Long id){
    ApiMessageDto<DeckDto> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    User user = userRepository.findById(principal.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
    Deck deck = deckRepository.findByIdAndUserId(id, user.getId())
        .orElseThrow(() -> new NotFoundException("Deck not found"));
    apiMessageDto.setData(deckMapper.fromDeckToDto(deck));
    apiMessageDto.setMessage("Get deck successfully");
    return apiMessageDto;
  }

  @PutMapping("/update")
  public ApiMessageDto<String> update(@RequestBody @Valid UpdateDeckForm request){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    Long userId = principal.getUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));
    Deck deck = deckRepository.findByIdAndUserId(request.getId(), user.getId()).orElseThrow(()
    -> new NotFoundException("Deck not found"));
    deckMapper.updateDeckToEntity(request, deck);
    deckRepository.save(deck);
    apiMessageDto.setMessage("Update deck successfully");
    return apiMessageDto;
  }

  @Transactional
  @DeleteMapping("/delete/{id}")
  public ApiMessageDto<String> delete(@PathVariable Long id){
    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserPrincipal)) {
      throw new RuntimeException("User not authenticated");
    }
    CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
    Long userId = principal.getUserId();
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found"));
    Deck deck = deckRepository.findByIdAndUserId(id, user.getId()).orElseThrow(()
        -> new NotFoundException("Deck not found"));
    plashCardRepository.deleteByDeckId(deck.getId());
    deckRepository.delete(deck);
    apiMessageDto.setMessage("Delete deck successfully");
    return apiMessageDto;
  }
}

package vn.ute.mobile.project.mapper;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import vn.ute.mobile.project.dto.notice.NoticeDto;
import vn.ute.mobile.project.form.notice.CreateNoticeForm;
import vn.ute.mobile.project.form.notice.UpdateNoticeForm;
import vn.ute.mobile.project.model.Notice;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {UserMapper.class})
public interface NoticeMapper {
  @Mapping(source = "noticeName", target = "noticeName")
  @Mapping(source = "noticeContent", target = "noticeContent")
  @BeanMapping(ignoreByDefault = true)
  Notice fromCreateFormToNotice(CreateNoticeForm createNoticeForm);

  @Mapping(source = "noticeName", target = "noticeName")
  @Mapping(source = "noticeContent", target = "noticeContent")
  @BeanMapping(ignoreByDefault = true)
  void updateNoticeForm(UpdateNoticeForm updateNoticeForm, @MappingTarget Notice notice);

  @Mapping(source = "id", target = "id")
  @Mapping(source = "user", target = "user", qualifiedByName = "fromUserToDto")
  @Mapping(source = "noticeName", target = "noticeName")
  @Mapping(source = "noticeContent", target = "noticeContent")
  @BeanMapping(ignoreByDefault = true)
  @Named("fromNoticeToDto")
  NoticeDto fromNoticeToDto(Notice notice);

  @IterableMapping(elementTargetType = NoticeDto.class, qualifiedByName = "fromNoticeToDto")
  List<NoticeDto> fromNoticeToDtoList(List<Notice> notices);
}

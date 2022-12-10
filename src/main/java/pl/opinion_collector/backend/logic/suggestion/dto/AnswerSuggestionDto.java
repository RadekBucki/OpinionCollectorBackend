package pl.opinion_collector.backend.logic.suggestion.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnswerSuggestionDto {
    @ApiModelProperty(notes = "ID of answered suggestion", example = "1", required = true)
    Integer suggestionId;
    @ApiModelProperty(notes = "New Status for suggestion", example = "DECLINED", required = true)
    SuggestionStatus suggestionStatus;
    @ApiModelProperty(notes = "Content of suggestion reply", example = "Thx for your suggestion", required = true)
    String suggestionReply;


    public enum SuggestionStatus {
        DECLINED, PENDING, DONE
    }
}


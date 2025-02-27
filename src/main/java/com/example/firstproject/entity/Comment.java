package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment toEntity(Article parentArticle, CommentDto commentDto) {
        // 예외 발생(2가지 경우)
        if (commentDto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 있으면 안됩니다.");
        }
        if (commentDto.getArticleId() != parentArticle.getId()) {
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        }
        // 엔티티 생성 및 변환
        Comment changedComment = new Comment(
                commentDto.getId(),
                parentArticle,
                commentDto.getNickname(),
                commentDto.getBody()
        );
        return changedComment;
    }

    public Comment patch(CommentDto commentDto) {
        // 예외 발생
        if (this.id != commentDto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        // 객체 갱신
        if (commentDto.getNickname() != null)
            this.nickname = commentDto.getNickname();
        if (commentDto.getBody() != null)
            this.body = commentDto.getBody();
        return this;
    }
}

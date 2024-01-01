package org.example.pages.comments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CommentObject {
    private int id;
    private final String comment;
    private final String author;
    private final String email;
    private final String url;
}

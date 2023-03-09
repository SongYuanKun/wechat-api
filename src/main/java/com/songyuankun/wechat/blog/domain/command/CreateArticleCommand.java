package com.songyuankun.wechat.blog.domain.command;

import com.songyuankun.wechat.blog.application.dto.ArticleDTO;
import lombok.Data;

@Data
public class CreateArticleCommand {
    private ArticleDTO articleDTO;
}

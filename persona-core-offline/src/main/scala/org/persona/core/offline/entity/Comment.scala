package org.persona.core.offline.entity

import java.sql.Date

case class Comment(id: Int, commentorId: Int, anonymous: Boolean,
                   postId: Int, replyId: Int, countVote: Int, commentTime: Date,
                   deleted: Boolean, tagAgree: Boolean, tagTop: Boolean, activeFlag: Boolean)

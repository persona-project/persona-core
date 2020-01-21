package org.persona.core.offline.entity

import java.sql.Date

case class Comment(id: Long, commentorId: Long, anonymous: Boolean,
                   postId: Long, replyId: Long, countVote: Int, commentTime: Date,
                   deleted: Boolean, tagAgree: Boolean, tagTop: Boolean, activeFlag: Boolean)

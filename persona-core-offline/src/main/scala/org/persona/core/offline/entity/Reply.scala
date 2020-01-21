package org.persona.core.offline.entity

import java.sql.Date

case class Reply(id: Long, replyerId: Long, anonymous: Boolean, postId: Long,
                 countVote: Int, countComment: Int, replyTime: Date,
                 deleted: Boolean, tagAgree: Boolean, tagTop: Boolean, activeFlag: Boolean)

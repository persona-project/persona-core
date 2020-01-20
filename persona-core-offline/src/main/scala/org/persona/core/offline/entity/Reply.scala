package org.persona.core.offline.entity

import java.sql.Date

case class Reply(id: Int, replyerId: Int, anonymous: Boolean, postId: Int,
                 countVote: Int, countComment: Int, replyTime: Date,
                 deleted: Boolean, tagAgree: Boolean, tagTop: Boolean, activeFlag: Boolean)

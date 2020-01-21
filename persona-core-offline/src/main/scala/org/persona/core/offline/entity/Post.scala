package org.persona.core.offline.entity

import java.sql.Date

case class Post(id: Long, `type`: Int, posterId: Long, postTime: Date, lastReplyTime: Date,
                anonymous: Boolean, tagAgree: Boolean, tagTop: Boolean, tagSolve: Boolean,
                tagLector: Boolean, countBrowse: Boolean, countReply: Boolean,
                countVote: Boolean, deleted: Boolean, activeFlag: Boolean
               )

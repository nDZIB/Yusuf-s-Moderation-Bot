/*
 *  GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/> Everyone is permitted to
 *  copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *  Yusuf Arfan Ismail
 *  The GNU General Public License is a free, copyleft license for software and other kinds of works.
 */

CREATE TABLE test
(
    user_id BIGINT NOT NULL PRIMARY KEY,
    guid_id BIGINT NOT NULL,
    warn_reason TEXT NOT NULL,
    amount_of_warns INTEGER NOT NULL
)
# Persona - Core

To describe the architecture of persona and store configuration files.



## Architecture

![arthictecure](https://raw.githubusercontent.com/persona-project/persona-core/master/.images/persona-architecture.png)



## Notes

1. `user_tag_value`, `moc_post`, `moc_reply`, `moc_comment` comes from mooc `MySql`.
2. `wda_mooc` maybe come from mooc `HDFS`.
3. `Spark` used for off-line data processing.
4. `Spark Streaming` used for real-time data processing.



## In indetermination

1. How to  arrange `persona - ml` module?

2. `Redis` has been chosen.
> How to choose `MySql`, `HBase` and `Redis`?  
>     - `Redis`: the data is easy to lose, but fastest.  
>     - `HBase`: data not lose. Is its deployment easy?  
>     - `MySql`: too slow.  

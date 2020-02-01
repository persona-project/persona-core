# Persona - Core

To describe the architecture of persona and store configuration files.


## Architecture

![arthictecure](https://raw.githubusercontent.com/persona-project/persona-core/master/.images/persona-architecture.png)


## Deployment

```bash
bash install.sh
```

The systemd services will be generated: `persona-offline`, `persona-realtime`, `persona-flume` and `persona-backend`.  
And you can use them as service.


## Notes

1. `user_tag_value`, `moc_post`, `moc_reply`, `moc_comment` comes from mooc `MySql`.
2. `wda_mooc` maybe come from mooc `HDFS`.
3. `Spark` used for off-line data processing.
4. `Spark Streaming` used for real-time data processing.
5. `Redis` has been chosen for data caching.
> How to choose `MySql`, `HBase` and `Redis`?  
>     - `Redis`: the data is easy to lose, but fastest.  
>     - `HBase`: data not lose. Is its deployment easy?  
>     - `MySql`: too slow.  


## In indetermination

1. How to arrange `persona - ml` module?


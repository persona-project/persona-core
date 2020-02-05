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


## Key points

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


## Notes

[persona大数据平台开发记录-1 业务逻辑数据导入](http://39.106.185.26/post.sh?name=2020-01-20_persona%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%B9%B3%E5%8F%B0%E5%BC%80%E5%8F%91%E8%AE%B0%E5%BD%95-1.md)

[persona大数据平台开发记录-2 离线数据处理](http://39.106.185.26/post.sh?name=2020-01-22_persona%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%B9%B3%E5%8F%B0%E5%BC%80%E5%8F%91%E8%AE%B0%E5%BD%95-2.md)

[persona大数据平台开发记录-3 实时日志收集与传输](http://39.106.185.26/post.sh?name=2020-01-27_persona%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%B9%B3%E5%8F%B0%E5%BC%80%E5%8F%91%E8%AE%B0%E5%BD%95-3.md)

[persona大数据平台开发记录-4 部署过程](http://39.106.185.26/post.sh?name=2020-01-31_persona%E5%A4%A7%E6%95%B0%E6%8D%AE%E5%B9%B3%E5%8F%B0%E5%BC%80%E5%8F%91%E8%AE%B0%E5%BD%95-4.md)


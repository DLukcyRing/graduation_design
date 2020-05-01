# 结构

数据库操作对应 XXXMapper.xml

xml与 dao-> XXXMapper 对应，同时需要再service里面的 XXXservice进行修改



ScoreDetailsMapper.xml这个文件有毒。。。必须把里面的东西注释掉才能用，我觉得是add/reduce这个标点符号的问题，不注释掉会导致login等全部数据库操作都报错



POST，GET等请求处理在 XXXcontroller 里面




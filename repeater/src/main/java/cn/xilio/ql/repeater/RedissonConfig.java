//package cn.xilio.ql.repeater;
//
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author xilio
// * @version 1.0
// * @description redis集群配置
// * @date 2024/2/18 1:07
// */
//@Configuration
//public class RedissonConfig {
//    @Bean
//    public Config config() {
//        Config config = new Config();
//        config.useClusterServers()
//                .addNodeAddress("redis://127.0.0.1:7181");
//        return config;
//        // config = Config.fromYAML(new File("config-file.yaml"));
//    }
//
//}

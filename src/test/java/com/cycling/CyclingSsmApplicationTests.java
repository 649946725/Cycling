package com.cycling;

import com.cycling.dao.UserDao;
import com.cycling.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author xpdxz
 * @ClassName CyclingSsmApplicationTests
 * @Description TODO
 * @Date 2022/3/13 21:50
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CyclingSsmApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private UserDao userDao;

    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

    private static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    @Test
    void createTestUser() throws IOException {
        File file = new File("D:\\Users\\xpdxz\\Desktop\\token.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        randomAccessFile.seek(0);
        for (int i = 0; i < 20000; i++) {
            User user = new User();
            user.setPassword("1597d20d1a17bd4ba28d042988ae1f12");
            user.setSalt("U8JS07lT");
            user.setPhone(getTel());
            userDao.registerUser(user);
            user.setPassword("123456");
            URL url = new URL("http://localhost:5678/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            String params = "phone=" + user.getPhone() + "&password=" + user.getPassword();
            outputStream.write(params.getBytes());
            outputStream.flush();
            System.out.println(connection.getHeaderFields());
            String token = connection.getHeaderField("Authorization");
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(token.getBytes());
            randomAccessFile.write("\n".getBytes());
        }
        randomAccessFile.close();
    }

//    @Test
//    void rabbit() throws JsonProcessingException {
//        Chat chat = new Chat();
//        chat.setContent("2dasdas");
//        rabbitTemplate.convertAndSend(RabbitMqConfig.NAME, chat);
//    }
//
//    @RabbitListener(queues = RabbitMqConfig.NAME)
//    public void consumer(Message message, Channel channel, @Header long deliveryTag, @Payload Chat chat) throws IOException {
//        System.out.println(new String(message.getBody()));
//        System.out.println(chat);
//        System.out.println(deliveryTag);
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//    }
}

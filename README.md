# CI Test:
[![<ORG_NAME>](https://circleci.com/gh/emretufekci/android-push-notification-without-fcm.svg?style=svg)](<LINK>)


# English 🇺🇸
## Android Bypassing FCM Push Notification

There is only way sending push notification without [GCM/FCM](https://firebase.google.com/products/cloud-messaging) which is Custom AlarmManager & BroadcastReceiver. For more detail please check [Schedule tasks with WorkManager paper on Android Developer guide](https://developer.android.com/topic/libraries/architecture/workmanager).

![overview-criteria](https://developer.android.com/images/topic/libraries/architecture/workmanager/overview-criteria.png)

## Dependencies

This example uses rabbitmq-5.10 library for java. Add following lines to gradle file.
```bash
    implementation 'com.rabbitmq:amqp-client:5.10.0'
    implementation 'org.slf4j:slf4j-api:1.7.25'
    implementation 'com.github.tony19:logback-android:1.1.1-12'
```

## AMQP Mode

To be able to use AMQP mode, you have to set your AMQP Server settings. Receive method called inside onCreate() method. So, when application wake up, nothing to do it's listening the queue.
```java
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_IP_ADRESS);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(VIRTUAL_HOST); //DEFAULT -> "/"
```

<p align="center"><img src="https://user-images.githubusercontent.com/23323000/106121999-41e63b80-6169-11eb-9968-8db53b9ba83b.png" width="300" height="600" /></p>

## Timer Mode

You can set timer from right top corner of app, then you can test AlarmManager & BroadcastReceiver background mode without internet connection.  


<p align="center"><img src="https://user-images.githubusercontent.com/23323000/106122349-aacdb380-6169-11eb-9f64-93b9f614e450.png" width="300" height="600" /></p>


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)


# Türkçe 🇹🇷
## Android FCM Bildirim Göndermeyi Aşma


[GCM/FCM](https://firebase.google.com/products/cloud-messaging) kullanmadan push notification göndermenin tek yolu Custom AlarmManager & BroadcastReceiver kullanmaktır. Bu konu hakkında detaylı bilgiyi android developer sayfasından alabilirsiniz: [Schedule tasks with WorkManager paper on Android Developer guide](https://developer.android.com/topic/libraries/architecture/workmanager).

![overview-criteria](https://developer.android.com/images/topic/libraries/architecture/workmanager/overview-criteria.png)

## Bağımlılıklar

Bu örnek rabbitmq-5.10 java kütüphanesini kullanıyor. Aşağıdaki bağımlılıkları gradle'a ekleyin.
```bash
    implementation 'com.rabbitmq:amqp-client:5.10.0'
    implementation 'org.slf4j:slf4j-api:1.7.25'
    implementation 'com.github.tony19:logback-android:1.1.1-12'
```

## AMQP Modu

Uygulamayı AMQP modunda çalıştırmak için AMQP sunucu ayarlarınızı yapmanız gerekmektedir. "Receive" kısmı onCreate() metodunun altında çağrıldığı için, uygulama ayağa kalktığı gibi kuyruk dinlenilmeye başlanır.
```java
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_IP_ADRESS);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(VIRTUAL_HOST); //DEFAULT -> "/"
```
<p align="center"><img src="https://user-images.githubusercontent.com/23323000/106121999-41e63b80-6169-11eb-9968-8db53b9ba83b.png" width="300" height="600" /></p>

## Zamanlayıcı Modı

İnternet ve sunucu ayarları yapmadan arkaplanda bildirim gönderme'yi test edebilirsiniz. Sağ üst kısımdan zamanlayıcıyı seçin ve uygulamayı kapatın.

<p align="center"><img src="https://user-images.githubusercontent.com/23323000/106122349-aacdb380-6169-11eb-9f64-93b9f614e450.png" width="300" height="600" /></p>


## Katkı
Büyük bir değişiklik ve pull request yapabilirsiniz. Değiştirmek istediğiniz konu hakkında lütfen öncelikle bir issue açın.

Değişiklikten sonra testlerinizi yaptığınızdan lütfen emin olun.

## Lisans
[MIT](https://choosealicense.com/licenses/mit/)

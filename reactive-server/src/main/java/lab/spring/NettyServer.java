package lab.spring;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@Component
public class NettyServer implements SmartLifecycle {

    private DisposableServer server;
    private boolean running = false;

    @Override
    public void start() {
        System.out.println("Starting Netty server...");
        this.server = HttpServer.create()
                .port(8081)
                .route(routes ->
                        routes
                                .get("/hello", (request, response) ->
                                        response.sendString(Mono.just("Hello from Netty Server!")))
                                .post("/echo", (request, response) ->
                                        response.send(request.receive().retain()))
                                .get("/time", (request, response) ->
                                        response.sendString(Mono.just("Current time: " + java.time.LocalDateTime.now())))
                )
                .bindNow();

        System.out.println("Netty server started on port 8081");
        this.running = true;

        // 非デーモンスレッドを作成して、アプリケーションが終了しないようにする
        Thread blockingThread = new Thread(() -> {
            try {
                // サーバーが起動している間はブロック
                server.onDispose().block();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        blockingThread.setDaemon(false);
        blockingThread.start();
    }

    @Override
    public void stop() {
        System.out.println("Stopping Netty server...");
        if (this.server != null) {
            this.server.disposeNow();
        }
        this.running = false;
        System.out.println("Netty server stopped");
    }

    @Override
    public boolean isRunning() {
        return running;
    }
}

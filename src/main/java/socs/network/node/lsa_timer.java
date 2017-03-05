package socs.network.node;

/**
 * Created by Xiru on 3/3/2017.
 */
public class lsa_timer implements Runnable{
    private final int SEND_INTERVAL = 1000;
    private final int LSA_TIMEOUT_INTERVAL = 2000;
    Router attached_router;

    public lsa_timer(Router current){
        attached_router = current;
    }
    public void run() {
        for(;;) {
            try {
                attached_router.router_send_lsa_init();
                attached_router.lsd.remove_outdated_link(LSA_TIMEOUT_INTERVAL);
                Thread.sleep(SEND_INTERVAL);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

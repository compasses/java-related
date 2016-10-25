package jet.mq.messageconsume.messageProc;

import org.springframework.stereotype.Service;

/**
 * Created by I311352 on 10/14/2016.
 */

@Service
public interface MessageHandler {
    void handleMsg(String action, Long tenantId, byte[] body);

    default void dowork(String type, String action, Long tenantId, byte[] body) {
        handleMsg(action, tenantId, body);
    }
}

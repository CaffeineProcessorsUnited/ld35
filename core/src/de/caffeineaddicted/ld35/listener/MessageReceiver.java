package de.caffeineaddicted.ld35.listener;

import de.caffeineaddicted.ld35.logic.Bundle;
import de.caffeineaddicted.ld35.logic.Message;

/**
 * Created by Malte on 17.04.2016.
 */
public interface MessageReceiver {
    public void onMessageReceived(Message message);

    public void onMessageReceived(Bundle bundle);

    public void onMessageReceived(Message message, Bundle bundle);
}

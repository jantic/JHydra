package jhydra.core.config.email;

import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * User: jantic
 * Date: 1/10/13
 */
public interface IEmailSettings {
    List<InternetAddress> getFailureRecipients();

    List<InternetAddress> getSuccessRecipients();

    InternetAddress getSender();
}

package jhydra.core.config.email;

import javax.mail.internet.InternetAddress;
import java.net.URI;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jantic
 * Date: 1/10/13
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IEmailSettings {
    List<InternetAddress> getFailureRecipients();

    List<InternetAddress> getSuccessRecipients();

    InternetAddress getSender();

    URI getSmtpHost();

    Boolean sendSuccessEmail();

    Boolean getSendFailureEmail();
}

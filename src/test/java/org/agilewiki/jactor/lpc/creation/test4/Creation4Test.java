package org.agilewiki.jactor.lpc.creation.test4;

import junit.framework.TestCase;
import org.agilewiki.jactor.JAMailboxFactory;
import org.agilewiki.jactor.Mailbox;
import org.agilewiki.jactor.MailboxFactory;
import org.agilewiki.jactor.factory.ActorFactory;
import org.agilewiki.jactor.factory.DefineActorType;
import org.agilewiki.jactor.factory.GetActorFactory;
import org.agilewiki.jactor.factory.JFactory;
import org.agilewiki.jactor.lpc.creation.A;

public class Creation4Test extends TestCase {
    public void test() {
        //System.out.println("####################################################");

        long c = 1;

        //long c = 100000000;
        //iterations per second = 200,400,801 (was 142,045,454 when Factory component was used)

        MailboxFactory mailboxFactory = JAMailboxFactory.newMailboxFactory(1);
        try {
            JFactory f = new JFactory(mailboxFactory.createMailbox());
            (new DefineActorType("A", A.class)).call(f);
            loop(c, f);
            loop(c, f);
            long t0 = System.currentTimeMillis();
            loop(c, f);
            long t1 = System.currentTimeMillis();
            long d = t1 - t0;
            if (d > 0)
                System.out.println(1000 * c / d);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mailboxFactory.close();
        }
    }

    void loop(long c, JFactory f) throws Exception {
        GetActorFactory gaf = new GetActorFactory("A");
        ActorFactory af = gaf.call(f);
        MailboxFactory mailboxFactory = f.getMailboxFactory();
        Mailbox m = mailboxFactory.createMailbox();
        int i = 0;
        while (i < c) {
            af.newActor(m, f);
            i += 1;
        }
    }
}
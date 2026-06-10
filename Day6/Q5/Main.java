package Day6.Q5;

package com.streamhub.dispatcher;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

// ==================== Plugin Interface ====================

interface WebhookIntegration {
    void sendWebhook(String payload);
}

// ==================== Slack Integration ====================

@Component
class SlackIntegration implements WebhookIntegration {

    @Override
    public void sendWebhook(String payload) {
        System.out.println("Slack Webhook Sent : " + payload);
    }
}

// ==================== Discord Integration ====================

@Component
class DiscordIntegration implements WebhookIntegration {

    @Override
    public void sendWebhook(String payload) {
        System.out.println("Discord Webhook Sent : " + payload);
    }
}

// ==================== Email Integration ====================

@Component
class EmailIntegration implements WebhookIntegration {

    @Override
    public void sendWebhook(String payload) {
        System.out.println("Email Webhook Sent : " + payload);
    }
}

// ==================== Dispatcher ====================

@Component
class WebhookDispatcher implements ApplicationContextAware, SmartInitializingSingleton {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {

        Map<String, WebhookIntegration> integrations =
                applicationContext.getBeansOfType(WebhookIntegration.class);

        System.out.println(
                "Routing Table Ready. Total Integrations Loaded : "
                        + integrations.size()
        );

        integrations.forEach(
                (beanName, bean) ->
                        System.out.println(
                                beanName + " -> "
                                        + bean.getClass().getSimpleName()
                        )
        );
    }
}

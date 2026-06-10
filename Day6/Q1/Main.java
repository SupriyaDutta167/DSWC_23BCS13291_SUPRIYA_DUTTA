
package com.quantifyx.trading;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

// ==================== TradingStrategy ====================

interface TradingStrategy {
    void executeTrade();
}

// ==================== AbstractStrategy ====================

abstract class AbstractStrategy implements TradingStrategy {
    protected String assetClass;
}

// ==================== MomentumStrategy ====================

@Component
class MomentumStrategy extends AbstractStrategy {

    public MomentumStrategy() {
        this.assetClass = "Equity";
    }

    @Override
    public void executeTrade() {
        System.out.println("Executing Momentum Strategy for " + assetClass);
    }
}

// ==================== ArbitrageStrategy ====================

@Component
class ArbitrageStrategy extends AbstractStrategy {

    public ArbitrageStrategy() {
        this.assetClass = "Forex";
    }

    @Override
    public void executeTrade() {
        System.out.println("Executing Arbitrage Strategy for " + assetClass);
    }
}

// ==================== MarketDataService ====================

@Service
class MarketDataService {

    public void loadMarketData() {
        System.out.println("Market Data Loaded");
    }
}

// ==================== AlertService ====================

@Service
class AlertService {

    public void sendAlert(String message) {
        System.out.println("ALERT : " + message);
    }
}

// ==================== TradingEngine ====================

@Component
class TradingEngine implements BeanNameAware, InitializingBean {

    private final MarketDataService marketDataService;
    private final List<TradingStrategy> strategies;

    private AlertService alertService;

    private String beanName;

    // Constructor Injection (Mandatory Dependencies)
    public TradingEngine(MarketDataService marketDataService,
                         List<TradingStrategy> strategies) {
        this.marketDataService = marketDataService;
        this.strategies = strategies;
    }

    // Setter Injection (Optional Dependency)
    @Autowired(required = false)
    public void setAlertService(AlertService alertService) {
        this.alertService = alertService;
    }

    // BeanNameAware
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println("Bean Name : " + beanName);
    }

    // Initialization Phase 1
    @PostConstruct
    public void warmUpCache() {
        System.out.println("Warming up market cache...");
        marketDataService.loadMarketData();
    }

    // Initialization Phase 2
    @Override
    public void afterPropertiesSet() {

        System.out.println("Performing Safety Validation...");

        if (marketDataService == null) {
            throw new IllegalStateException("MarketDataService Missing");
        }

        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalStateException("No Trading Strategies Loaded");
        }

        System.out.println("Validation Successful");
    }

    public void startTrading() {

        System.out.println("Trading Engine Started");

        for (TradingStrategy strategy : strategies) {
            strategy.executeTrade();
        }

        if (alertService != null) {
            alertService.sendAlert("High Risk Trade Monitoring Enabled");
        }
    }

    // Destruction Phase
    @PreDestroy
    public void closePositions() {
        System.out.println("Closing all open market positions...");
    }
}

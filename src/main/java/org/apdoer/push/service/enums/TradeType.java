

package org.apdoer.push.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author apdoer
 */

@Getter
@AllArgsConstructor
public enum TradeType {
    TRADE_CASH(1, "现货"),
    TRADE_FUTURE(2, "期货");

    private Integer code;
    private String title;

}

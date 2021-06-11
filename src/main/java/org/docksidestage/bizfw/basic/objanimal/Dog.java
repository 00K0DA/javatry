/*
 * Copyright 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The object for dog(犬).
 * @author jflute
 * @author 81704
 */
public class Dog extends Animal implements FastRunner, Eat {
    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(Dog.class);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Dog() {
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    protected String getBarkWord() {
        return "wan"; // bow? in English
    }

    // ===================================================================================
    //                                                                              Runner
    //                                                                              ======
    @Override
    public void run() {
        logger.debug("I'm Pochi!!, ...Running now");
    }

    // ===================================================================================
    //                                                                                 EAT
    //                                                                                 ===
    @Override
    public void eat() {
        if (this.getInitialHitPoint() == this.hitPoint) {
            logger.debug("犬はお腹いっぱいだ");
        } else {
            logger.debug("犬は肉を食べた");
            this.hitPoint += 2;
            // HP無限増殖防止
            if (this.getInitialHitPoint() < this.hitPoint) {
                this.hitPoint = this.getInitialHitPoint();
            }
        }
    }
}

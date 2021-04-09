## Description

Unable to draw a card when running the team game.

## Expected Behavior

should be able to draw a card and determine movable pieces

## Current Behavior

throws an exception and posts the alert

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. select Team option in Scoring
 2. select a card
 
## Failure Logs
    Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
	at ooga.view.GameScreen.updateBackEnd(GameScreen.java:188)
	at ooga.view.CardView.presentCard(CardView.java:79)
	at ooga.view.CardView.lambda$createGameDeck$0(CardView.java:62)
	at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:86)
	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
	at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:49)
	at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
	at javafx.graphics/javafx.scene.Node.fireEvent(Node.java:8885)
	at javafx.controls/javafx.scene.control.Button.fire(Button.java:203)
	at javafx.controls/com.sun.javafx.scene.control.behavior.ButtonBehavior.mouseReleased(ButtonBehavior.java:206)
	at javafx.controls/com.sun.javafx.scene.control.inputmap.InputMap.handle(InputMap.java:274)
	at javafx.base/com.sun.javafx.event.CompositeEventHandler$NormalEventHandlerRecord.handleBubblingEvent(CompositeEventHandler.java:247)
	at javafx.base/com.sun.javafx.event.CompositeEventHandler.dispatchBubblingEvent(CompositeEventHandler.java:80)
	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:234)
	at javafx.base/com.sun.javafx.event.EventHandlerManager.dispatchBubblingEvent(EventHandlerManager.java:191)
	at javafx.base/com.sun.javafx.event.CompositeEventDispatcher.dispatchBubblingEvent(CompositeEventDispatcher.java:59)
	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:58)
	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base/com.sun.javafx.event.BasicEventDispatcher.dispatchEvent(BasicEventDispatcher.java:56)
	at javafx.base/com.sun.javafx.event.EventDispatchChainImpl.dispatchEvent(EventDispatchChainImpl.java:114)
	at javafx.base/com.sun.javafx.event.EventUtil.fireEventImpl(EventUtil.java:74)
	at javafx.base/com.sun.javafx.event.EventUtil.fireEvent(EventUtil.java:54)
	at javafx.base/javafx.event.Event.fireEvent(Event.java:198)
	at javafx.graphics/javafx.scene.Scene$MouseHandler.process(Scene.java:3890)
	at javafx.graphics/javafx.scene.Scene.processMouseEvent(Scene.java:1885)
	at javafx.graphics/javafx.scene.Scene$ScenePeerListener.mouseEvent(Scene.java:2618)
	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:409)
	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler$MouseEventNotification.run(GlassViewEventHandler.java:299)
	at java.base/java.security.AccessController.doPrivileged(AccessController.java:391)
	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.lambda$handleMouseEvent$2(GlassViewEventHandler.java:447)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.runWithoutRenderLock(QuantumToolkit.java:412)
	at javafx.graphics/com.sun.javafx.tk.quantum.GlassViewEventHandler.handleMouseEvent(GlassViewEventHandler.java:446)
	at javafx.graphics/com.sun.glass.ui.View.handleMouseEvent(View.java:556)
	at javafx.graphics/com.sun.glass.ui.View.notifyMouse(View.java:942)
	at javafx.graphics/com.sun.glass.ui.mac.MacView.notifyMouse(MacView.java:127)

## Hypothesis for Fixing the Bug

teamsInRulesInitialized() verifies the issue; to fix the bug I just need to move the creation of the rules class after the teams are made

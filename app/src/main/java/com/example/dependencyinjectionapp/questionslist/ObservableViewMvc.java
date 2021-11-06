package com.example.dependencyinjectionapp.questionslist;

public interface ObservableViewMvc<ListenerType> extends ViewMvc {
    void registerListener(ListenerType listenerType);
    void unregisterListener(ListenerType listenerType);

}

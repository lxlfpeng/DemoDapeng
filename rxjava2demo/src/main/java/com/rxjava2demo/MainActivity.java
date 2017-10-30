package com.rxjava2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private TextView mTextView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_main);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // testOne();//普通测试
                //testTwo();//dispose测试
                // testThr();//观察者测试
                //test4();//测试线程调用
                //testMap();//map操作符测试
                //testFlatMap();//FlatMap操作符测试
                // testConcatMap();//concatMap操作符
                //testZip();//zip操作符
                //testBackpressure();//Backpressure测试
                testFlowable();//flowable测试
            }
        });
    }

    private void testFlowable() {
     /*   Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();


            }
        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                s.request(2);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });*/
        ArrayList<String> strings = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            strings.add("测试代码--->" + x);
        }
        Observable.fromIterable(strings).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s--->" + s);
            }
        });
    }

    private void testBackpressure() {
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; ; i++) {   //无限循环发事件
                    e.onNext(i);
                }
            }
        });

        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("a");
            }
        });
        Observable.zip(integerObservable, stringObservable, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s--->" + s);
            }
        });
    }

    private void testZip() {
       /* Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.d(TAG, "subscribe--->" + 1);
                e.onNext(2);
                Log.d(TAG, "subscribe--->" + 2);

                e.onNext(3);
                Log.d(TAG, "subscribe--->" + 3);

                e.onNext(4);
                Log.d(TAG, "subscribe--->" + 4);

            }
        }).subscribeOn(Schedulers.io());
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("屠大狗");
                Log.d(TAG, "subscribe--->屠大狗");
                e.onNext("屠二狗");
                Log.d(TAG, "subscribe--->屠二狗");
                e.onNext("屠三狗");
                Log.d(TAG, "subscribe--->屠三狗");

            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(integerObservable, stringObservable, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s--->" + s);
            }
        });*/

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);

                Log.d(TAG, "emit 2");
                emitter.onNext(2);

                Log.d(TAG, "emit 3");
                emitter.onNext(3);

                Log.d(TAG, "emit 4");
                emitter.onNext(4);

                Log.d(TAG, "emit complete1");
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");

                Log.d(TAG, "emit B");
                emitter.onNext("B");

                Log.d(TAG, "emit C");
                emitter.onNext("C");

                Log.d(TAG, "emit complete2");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });


    }

    private void testConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s--->" + s);
            }
        });
    }

    private void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);

            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s--->" + s);
            }
        });
    }

    private void testMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "屠" + integer + "狗";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "s--->" + s);
            }
        });
    }

    private void test4() {
        //多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
        //多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "subscribe--" + "thread--->" + Thread.currentThread().getName());
                e.onNext("八哥");

            }
        }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.single()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "subscribe--" + "accept--->" + Thread.currentThread().getName());
            }
        });
    }

    private void testThr() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept--->" + integer);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "accept--->" + throwable.toString());

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.d(TAG, "run--->");

            }
        });
    }

    private void testTwo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe--->");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext-->" + value);
                if (value == 2) {
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError-->" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete-->");
            }
        });
    }

    private void testOne() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("金毛");
                e.onNext("哈士奇");
                e.onNext("萨摩耶");
                e.onNext("柯基");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe--->");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext-->" + value);

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError-->" + e.toString());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete-->");

            }
        });

    }
}

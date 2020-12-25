# Android Sample App
- Android Simple Source




## Structure
- Applandeo Material Calendar View
- MP Chart
- RxJava
___




### branch rx
- **buffer**
  > buffer를 활용한 onBackPressed
            ```(kotlin)
               >backButtonSubject
                    .map { System.currentTimeMillis() }
                    .buffer(2, 1)
                    .map { val (first, second) = it; first to second }
                    .filter { (first, second) -> second - first < 1000 }
                    .subscribe { finish() }
                    .let(compositeDisposable::add)
             ```
- **map**
   dddasdf
- **filter**
- **debounce**
- **throttleFirst**
- **merge**
- **combineLatest**
- **switchMap**
- **takeUntil**
- **withFromLatest**
- **retryWhen**
- **Hot Observable**
___




### Blog 
- [Tistory](https://class-programming.tistory.com/)
- [Notion](https://www.notion.so/fundevjay/Posting-ddf96b24265e414fb2d9e8fc5d388b80)
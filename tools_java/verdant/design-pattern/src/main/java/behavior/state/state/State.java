package behavior.state.state;

/**
 * Created by Administrator on 2016/3/28.
 */
public abstract class State{
    public abstract void handlepush(Context c);
    public abstract void handlepull(Context c);
    public abstract void getcolor();
}

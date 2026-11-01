package in.littlesk.plugin.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Messages {
    private Messages() { }

    public static final List<String> ONLINE_MODE_MESSAGE = Collections.unmodifiableList(Arrays.<String>asList(
            "Server is running in online mode. GetSkinFrom-LitteSkins will not take any effect on online mode.",
            "服务器当前处于在线模式，GetSkinFrom-LitteSkins 不会生效。"
    ));

    public static final List<String> DISABLE_MESSAGE = Collections.unmodifiableList(Arrays.<String>asList(
            "GetSkinFrom-LitteSkins will be disabled.",
            "GetSkinFrom-LitteSkins 将被卸载。"
    ));

    public static final List<String> INCOMPATIBLE_SERVER_SHUTDOWN_MESSAGE = Collections.unmodifiableList(Arrays.<String>asList(
            "The server will be shut down right after initialization. Please remove the plugin before starting the server again.",
            "服务器将在初始完成化后立即关闭。请在再次启动服务器前移除本插件。"
    ));

    public static final List<String> INCOMPATIBLE_SERVER_MESSAGE_BUKKIT = Collections.unmodifiableList(Stream.<List<String>>of(
            Arrays.<String>asList(
                "Incompatible server platform detected. GetSkinFrom-LitteSkins currently supports only Paper 1.12.2+ and some Paper forks.",
                "检测到不支持的服务端。GetSkinFrom-LitteSkins 目前仅支持 Paper 1.12.2+ 及部分 Paper 分支。"
            ),
            INCOMPATIBLE_SERVER_SHUTDOWN_MESSAGE
    ).flatMap(Collection::stream).collect(Collectors.toList()));

    public static final List<String> INCOMPATIBLE_SERVER_MESSAGE_VELOCITY = Collections.unmodifiableList(Stream.<List<String>>of(
            Arrays.<String>asList(
                    "Incompatible server platform detected. GetSkinFrom-LitteSkins currently supports only Velocity 3.0+.",
                    "检测到不支持的服务端。GetSkinFrom-LitteSkins 目前仅支持 Velocity 3.0+。"
            ),
            INCOMPATIBLE_SERVER_SHUTDOWN_MESSAGE
    ).flatMap(Collection::stream).collect(Collectors.toList()));

    public static final List<String> INCOMPATIBLE_SERVER_STAY_TUNED_MESSAGE = Collections.unmodifiableList(Arrays.<String>asList(
            "Support for more servers platforms may be added in the future. Stay tuned!",
            "我们未来可能会添加对更多服务端的支持，敬请期待！",
            "https://github.com/Minecraft0122/GetSkinFrom-LitteSkins"
    ));
}

.class public Lcom/regus/mj/ServiceManagerWraper;
.super Ljava/lang/Object;
.source "ServiceManagerWraper.java"


# direct methods
.method public constructor <init>()V
    .registers 1

    .prologue
    .line 14
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static hookPMS(Landroid/content/Context;)V
    .registers 5
    .param p0, "context"    # Landroid/content/Context;

    .prologue
    .line 48
    const-string v2, "REGUS_SIGN"

    .line 49
    .local v2, "sign":Ljava/lang/String;
    const-string v0, "PKG"

    .line 51
    .local v0, "pkg":Ljava/lang/String;
    move-object v1, v2

    .line 52
    .local v1, "qqSign":Ljava/lang/String;
    const/4 v3, 0x0

    invoke-static {p0, v1, v0, v3}, Lcom/regus/mj/ServiceManagerWraper;->hookPMS(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;I)V

    .line 53
    return-void
.end method

.method public static hookPMS(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;I)V
    .registers 20
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "signed"    # Ljava/lang/String;
    .param p2, "appPkgName"    # Ljava/lang/String;
    .param p3, "hashCode"    # I

    .prologue
    .line 20
    :try_start_0
    const-string v12, "android.app.ActivityThread"

    invoke-static {v12}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v2

    .line 21
    .local v2, "activityThreadClass":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    const-string v12, "currentActivityThread"

    const/4 v13, 0x0

    new-array v13, v13, [Ljava/lang/Class;

    .line 22
    invoke-virtual {v2, v12, v13}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v4

    .line 23
    .local v4, "currentActivityThreadMethod":Ljava/lang/reflect/Method;
    const/4 v12, 0x0

    const/4 v13, 0x0

    new-array v13, v13, [Ljava/lang/Object;

    invoke-virtual {v4, v12, v13}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 25
    .local v3, "currentActivityThread":Ljava/lang/Object;
    const-string v12, "sPackageManager"

    invoke-virtual {v2, v12}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v11

    .line 26
    .local v11, "sPackageManagerField":Ljava/lang/reflect/Field;
    const/4 v12, 0x1

    invoke-virtual {v11, v12}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 27
    invoke-virtual {v11, v3}, Ljava/lang/reflect/Field;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v10

    .line 29
    .local v10, "sPackageManager":Ljava/lang/Object;
    const-string v12, "android.content.pm.IPackageManager"

    invoke-static {v12}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v6

    .line 31
    .local v6, "iPackageManagerInterface":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    invoke-virtual {v6}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object v12

    const/4 v13, 0x1

    new-array v13, v13, [Ljava/lang/Class;

    const/4 v14, 0x0

    aput-object v6, v13, v14

    new-instance v14, Lcom/regus/mj/PmsHookBinderInvocationHandler;

    const/4 v15, 0x0

    move-object/from16 v0, p1

    move-object/from16 v1, p2

    invoke-direct {v14, v10, v0, v1, v15}, Lcom/regus/mj/PmsHookBinderInvocationHandler;-><init>(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;I)V

    .line 30
    invoke-static {v12, v13, v14}, Ljava/lang/reflect/Proxy;->newProxyInstance(Ljava/lang/ClassLoader;[Ljava/lang/Class;Ljava/lang/reflect/InvocationHandler;)Ljava/lang/Object;

    move-result-object v9

    .line 35
    .local v9, "proxy":Ljava/lang/Object;
    invoke-virtual {v11, v3, v9}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V

    .line 37
    invoke-virtual/range {p0 .. p0}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v8

    .line 38
    .local v8, "pm":Landroid/content/pm/PackageManager;
    invoke-virtual {v8}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v12

    const-string v13, "mPM"

    invoke-virtual {v12, v13}, Ljava/lang/Class;->getDeclaredField(Ljava/lang/String;)Ljava/lang/reflect/Field;

    move-result-object v7

    .line 39
    .local v7, "mPmField":Ljava/lang/reflect/Field;
    const/4 v12, 0x1

    invoke-virtual {v7, v12}, Ljava/lang/reflect/Field;->setAccessible(Z)V

    .line 40
    invoke-virtual {v7, v8, v9}, Ljava/lang/reflect/Field;->set(Ljava/lang/Object;Ljava/lang/Object;)V
    :try_end_5b
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_5b} :catch_5c

    .line 44
    .end local v2    # "activityThreadClass":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    .end local v3    # "currentActivityThread":Ljava/lang/Object;
    .end local v4    # "currentActivityThreadMethod":Ljava/lang/reflect/Method;
    .end local v6    # "iPackageManagerInterface":Ljava/lang/Class;, "Ljava/lang/Class<*>;"
    .end local v7    # "mPmField":Ljava/lang/reflect/Field;
    .end local v8    # "pm":Landroid/content/pm/PackageManager;
    .end local v9    # "proxy":Ljava/lang/Object;
    .end local v10    # "sPackageManager":Ljava/lang/Object;
    .end local v11    # "sPackageManagerField":Ljava/lang/reflect/Field;
    :goto_5b
    return-void

    .line 41
    :catch_5c
    move-exception v5

    .line 42
    .local v5, "e":Ljava/lang/Exception;
    const-string v12, "jw"

    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "hook pms error:"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-static {v5}, Landroid/util/Log;->getStackTraceString(Ljava/lang/Throwable;)Ljava/lang/String;

    move-result-object v14

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v13

    invoke-static {v12, v13}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    goto :goto_5b
.end method

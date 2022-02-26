## relolver

简单的使用

1.打包 relolver-common、打包 relolver-core

2.运行 RelolverWebApplication

访问

http://127.0.0.1:8080/index

index页面源文件：

```
---
layout: default
title:Demo
page:true
---

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
    <style type="text/css">
        .hello {
            background-color: azure;
            padding: 10px;
            font-size: 20px;
        }
    </style>
</head>
<body>
<p class="hello">
    Hello {{username | append: " , welcome to Relolver!" }}
</p>

<div class="container">
    <table border="1">
        <thead>
        <tr>
            <td>Name</td>
            <td>Age</td>
        </tr>
        </thead>
        <tbody>
        {% for user in users %}
        <tr>
            <td>{{user.name}}</td>
            {% if user.age == 5 %}
            <td>binggo</td>
            {% else %}
            <td>{{user.age}}</td>
            {% endif %}
        </tr>
        {% endfor %}
        </tbody>
    </table>
    {% assign code="20001" %}
    {% include test.html text="this is include text hahahaha" text2 = "haha this is include text too" text3 = {{ code }}
    %}
</div>
</body>
</html>
```

访问后展示效果：

![image-20220225110947957](img/img1.png)

## 字符串、模板文件类别渲染：

源文件：

```
直接渲染：
{{username}}:{{password}}


对象迭代： 【此功能列入计划但未完成】
{%- for key in object.keySet() -%}
{{key}}={{value}}
{%- endfor -%}


加上判断语句：
{%- if username == "ximeng" -%}
    hello, this is if
{%- endif -%}


if else 语句：
{%- if username == "ximeng" -%}
    hello, this is if.
{%- elsif username == "flier" -%}
    hello, this is else if.
{%- else username == "flier" -%}
    hello, this is else.
{%- endif -%}


if嵌套for：
{%- if username == "flier" -%}
    {%- for user in users -%}
        {{user.name}}
    {%- endfor -%}
{%- endif -%}


原生的：包含的内容不会进行解析
{% raw %}
    {{username}}:{{password}}

    {%- if username == "flier" -%}
        {%- for user in users -%}
            {{user.name}}
        {%- endfor -%}
    {%- endif -%}
{% endraw %}
```

渲染代码：

```java
@Test
public void testRender() {
    String resource = "src/main/resources/template";
    String templateName = "template";
    String suffix = ".txt";
    JSONObject object = new JSONObject() {{
        put("username", "flier");
        put("password", "123456");
    }};
    List<User> users = new ArrayList<User>();
    for (int i = 0; i < 10; i++) {
        User user = new User();
        user.setName("flier" + i);
        user.setAge(i);
        users.add(user);
    }
    object.put("users", users);
    Map<String, String> map = new HashMap<>(2);
    object.put("object", map);
    String render = RelolverControl.render(resource, templateName, suffix, JSON.toJSONString(object));
    System.out.println(render);
}
```

执行结果：

```
com.flier.common.exception.ParamNotFoundException: object.keySet() is not collection or arrray
直接渲染：
flier:123456


对象迭代：



加上判断语句：



if else 语句：

    hello, this is else if.



if嵌套for：

    
        flier0
    
        flier1
    
        flier2
    
        flier3
    
        flier4
    
        flier5
    
        flier6
    
        flier7
    
        flier8
    
        flier9
    



原生的：包含的内容不会进行解析

    {{username}}:{{password}}

    {% if username == "flier" %}
        {% for user in users %}
            {{user.name}}
        {% endfor %}
    {% endif %}
```

另外有各种扩展函数功能，具体参考 test下pipline包的单元测试。



## 再来看一个复杂列子的实现

将以下json 渲染成可阅读的形式

```json
{
    "status": "completed",
    "timestamp": 1639740348,
    "system_info": {
        "os_name": "Windows",
        "os_version": "10.0.17763",
        "os_build": "",
        "cpu_arch": "x86",
        "device_model": ""
    },
    "crashed": true,
    "crash_reason": "EXCEPTION_INT_DIVIDE_BY_ZERO",
    "assertion": "",
    "stacktraces": [
        {
            "thread_id": 32920,
            "is_requesting": true,
            "registers": {
                "eax": "0x3",
                "ebp": "0x2f3fd80",
                "ebx": "0xdfed8",
                "ecx": "0x0",
                "edi": "0xf81ca20",
                "edx": "0x0",
                "eflags": "0x10246",
                "eip": "0xf8f78b8",
                "esi": "0x86f584",
                "esp": "0x2f3fd3c"
            },
            "frames": [
                {
                    "status": "symbolicated",
                    "original_index": 0,
                    "instruction_addr": "0xf8f78b8",
                    "package": "h:\\code\\test_system.cpp",
                    "symbol": "Zeus::CrashSystem::TestCrash",
                    "sym_addr": "0xf8f78b0",
                    "function": "Zeus::CrashSystem::TestCrash",
                    "filename": "crash_system.cpp",
                    "abs_path": "h:\\code\\test_system.cpp",
                    "lineno": 174,
                    "trust": "context"
                },
                {
                    "status": "symbolicated",
                    "original_index": 1,
                    "instruction_addr": "0x9530f6",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "Test",
                    "sym_addr": "0x9530a0",
                    "function": "Test",
                    "filename": "ds_service.cpp",
                    "abs_path": "tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std",
                    "lineno": 44,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x953671",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "DSService::Main::__l2::<lambda_af106cb3d19e5ac4e87ed1743a9e2573>::operator()",
                    "function": "DSService::Main::__l2::<lambda_af106cb3d19e5ac4e87ed1743a9e2573>::operator()",
                    "filename": "ds_service.cpp",
                    "abs_path": "tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std",
                    "lineno": 51,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x953671",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Invoker_functor::_Call",
                    "function": "std::_Invoker_functor::_Call",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1375,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x953671",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::invoke",
                    "function": "std::invoke",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1443,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x953671",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std",
                    "function": "tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 240,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x953671",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std",
                    "sym_addr": "0x9535f0",
                    "function": "tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 247,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 3,
                    "instruction_addr": "0x952e39",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Pad::_Call_func",
                    "sym_addr": "0x952e30",
                    "function": "std::_Pad::_Call_func",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 209,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 4,
                    "instruction_addr": "0x77cd892e",
                    "package": "C:\\Windows\\System32\\ucrtbase.dll",
                    "symbol": "thread_start<unsigned int (__stdcall*)(void *)>",
                    "sym_addr": "0x77cd88f0",
                    "function": "thread_start<unsigned int (__stdcall*)(void *)>",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 5,
                    "instruction_addr": "0x754b0418",
                    "package": "C:\\Windows\\System32\\kernel32.dll",
                    "symbol": "@BaseThreadInitThunk@12",
                    "sym_addr": "0x754b0400",
                    "function": "@BaseThreadInitThunk@12",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 6,
                    "instruction_addr": "0x77e374ec",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "__RtlUserThreadStart",
                    "sym_addr": "0x77e374be",
                    "function": "__RtlUserThreadStart",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x77e374bc",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "_RtlUserThreadStart@8",
                    "sym_addr": "0x77e374a2",
                    "function": "_RtlUserThreadStart@8",
                    "lineno": 0,
                    "trust": "cfi"
                }
            ]
        },
        {
            "thread_id": 14624,
            "is_requesting": false,
            "registers": {
                "eax": "0x77cd88f0",
                "ebp": "0x303f7b8",
                "ebx": "0x0",
                "ecx": "0x0",
                "edi": "0x10f5bc",
                "edx": "0x0",
                "eflags": "0x206",
                "eip": "0x77e433dc",
                "esi": "0x0",
                "esp": "0x303f774"
            },
            "frames": [
                {
                    "status": "symbolicated",
                    "original_index": 0,
                    "instruction_addr": "0x77e433dc",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "NtWaitForAlertByThreadId@8",
                    "sym_addr": "0x77e433d0",
                    "function": "NtWaitForAlertByThreadId@8",
                    "lineno": 0,
                    "trust": "context"
                },
                {
                    "status": "symbolicated",
                    "original_index": 1,
                    "instruction_addr": "0x77e33543",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "RtlSleepConditionVariableSRW",
                    "sym_addr": "0x77e33460",
                    "function": "RtlSleepConditionVariableSRW",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x76243a52",
                    "package": "C:\\Windows\\System32\\KERNELBASE.dll",
                    "symbol": "SleepConditionVariableSRW@16",
                    "sym_addr": "0x76243a30",
                    "function": "SleepConditionVariableSRW@16",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 3,
                    "instruction_addr": "0xf83a6d0",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "__crtSleepConditionVariableSRW",
                    "sym_addr": "0xf83a6ac",
                    "function": "__crtSleepConditionVariableSRW",
                    "filename": "winapisupp.cpp",
                    "abs_path": "h:\\code\\testmer_impl.cppp",
                    "lineno": 624,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 4,
                    "instruction_addr": "0xf81bff7",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "Concurrency::details::stl_critical_section_win7::native_handle",
                    "sym_addr": "0x10f7fffff",
                    "function": "Concurrency::details::stl_critical_section_win7::native_handle",
                    "filename": "primitives.h",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 187,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 5,
                    "instruction_addr": "0xf81bfcc",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "Concurrency::details::stl_condition_variable_win7::wait",
                    "sym_addr": "0xf81bfc0",
                    "function": "Concurrency::details::stl_condition_variable_win7::wait",
                    "filename": "primitives.h",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 210,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 6,
                    "instruction_addr": "0xf81c19b",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "do_wait",
                    "sym_addr": "0xf81c15b",
                    "function": "do_wait",
                    "filename": "cond.c",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 74,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0xf81c22f",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "_Cnd_wait",
                    "sym_addr": "0xf81c220",
                    "function": "_Cnd_wait",
                    "filename": "cond.c",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 105,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 8,
                    "instruction_addr": "0xf1070fc",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "std::_Cnd_waitX",
                    "function": "std::_Cnd_waitX",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 95,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 8,
                    "instruction_addr": "0xf1070fc",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "std::condition_variable::wait",
                    "function": "std::condition_variable::wait",
                    "filename": "mutex",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\mutex",
                    "lineno": 565,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 8,
                    "instruction_addr": "0xf1070fc",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "std::condition_variable::wait<<lambda_0a15a138323b69b6cc4b76083e374717> >",
                    "sym_addr": "0xf1070d0",
                    "function": "std::condition_variable::wait<<lambda_0a15a138323b69b6cc4b76083e374717> >",
                    "filename": "mutex",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\mutex",
                    "lineno": 572,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 9,
                    "instruction_addr": "0xf10b221",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "ThreadPool::GetTask",
                    "sym_addr": "0xf10b1b0",
                    "function": "ThreadPool::GetTask",
                    "filename": "threadpool.hpp",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 101,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 10,
                    "instruction_addr": "0xf10be24",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "ThreadPool::Schedual",
                    "sym_addr": "0xf10bde0",
                    "function": "ThreadPool::Schedual",
                    "filename": "threadpool.hpp",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 118,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 11,
                    "instruction_addr": "0xf10d9a4",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "std::_Invoker_pmf_pointer::_Call",
                    "function": "std::_Invoker_pmf_pointer::_Call",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1340,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 11,
                    "instruction_addr": "0xf10d9a4",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "std::invoke",
                    "function": "std::invoke",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1443,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 11,
                    "instruction_addr": "0xf10d9a4",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "h:\\code\\testmer_impl.cpp",
                    "function": "h:\\code\\testmer_impl.cpp",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 240,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 11,
                    "instruction_addr": "0xf10d9a4",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "h:\\code\\testmer_impl.cpp",
                    "sym_addr": "0xf10d920",
                    "function": "h:\\code\\testmer_impl.cpp",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 247,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 12,
                    "instruction_addr": "0xf10d2d5",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "h:\\code\\testmer_impl.cpp",
                    "sym_addr": "0xf10d2d0",
                    "function": "h:\\code\\testmer_impl.cpp",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 232,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 13,
                    "instruction_addr": "0xf10c7a9",
                    "package": "h:\\code\\testmer_impl.cpp",
                    "symbol": "std::_Pad::_Call_func",
                    "sym_addr": "0xf10c7a0",
                    "function": "std::_Pad::_Call_func",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 209,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 14,
                    "instruction_addr": "0x77cd892e",
                    "package": "C:\\Windows\\System32\\ucrtbase.dll",
                    "symbol": "thread_start<unsigned int (__stdcall*)(void *)>",
                    "sym_addr": "0x77cd88f0",
                    "function": "thread_start<unsigned int (__stdcall*)(void *)>",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 15,
                    "instruction_addr": "0x754b0418",
                    "package": "C:\\Windows\\System32\\kernel32.dll",
                    "symbol": "@BaseThreadInitThunk@12",
                    "sym_addr": "0x754b0400",
                    "function": "@BaseThreadInitThunk@12",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 16,
                    "instruction_addr": "0x77e374ec",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "__RtlUserThreadStart",
                    "sym_addr": "0x77e374be",
                    "function": "__RtlUserThreadStart",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 17,
                    "instruction_addr": "0x77e374bc",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "_RtlUserThreadStart@8",
                    "sym_addr": "0x77e374a2",
                    "function": "_RtlUserThreadStart@8",
                    "lineno": 0,
                    "trust": "cfi"
                }
            ]
        },
        {
            "thread_id": 37844,
            "is_requesting": false,
            "registers": {
                "eax": "0x0",
                "ebp": "0x327fd1c",
                "ebx": "0xffffffff",
                "ecx": "0x0",
                "edi": "0x0",
                "edx": "0x0",
                "eflags": "0x206",
                "eip": "0x77e433dc",
                "esi": "0x0",
                "esp": "0x327fcd4"
            },
            "frames": [
                {
                    "status": "symbolicated",
                    "original_index": 0,
                    "instruction_addr": "0x77e433dc",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "NtWaitForAlertByThreadId@8",
                    "sym_addr": "0x77e433d0",
                    "function": "NtWaitForAlertByThreadId@8",
                    "lineno": 0,
                    "trust": "context"
                },
                {
                    "status": "symbolicated",
                    "original_index": 1,
                    "instruction_addr": "0x77eafcd9",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "RtlSleepConditionVariableCS@12",
                    "sym_addr": "0x77eafc20",
                    "function": "RtlSleepConditionVariableCS@12",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 2,
                    "instruction_addr": "0x762d01df",
                    "package": "C:\\Windows\\System32\\KERNELBASE.dll",
                    "symbol": "SleepConditionVariableCS@12",
                    "sym_addr": "0x762d01c0",
                    "function": "SleepConditionVariableCS@12",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 3,
                    "instruction_addr": "0x99ced6",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "Zeus::EventImpl::WaitTimeout",
                    "sym_addr": "0x99ce90",
                    "function": "Zeus::EventImpl::WaitTimeout",
                    "filename": "event_impl.cpp",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 110,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 4,
                    "instruction_addr": "0x99ce82",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::chrono::duration_cast",
                    "function": "std::chrono::duration_cast",
                    "filename": "chrono",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 556,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 4,
                    "instruction_addr": "0x99ce82",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "Zeus::EventImpl::WaitTimeout",
                    "sym_addr": "0x99ce60",
                    "function": "Zeus::EventImpl::WaitTimeout",
                    "filename": "event_impl.cpp",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 29,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 5,
                    "instruction_addr": "0x99cce0",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "Zeus::RelativeTimerImpl::Wait",
                    "sym_addr": "0x99ccc0",
                    "function": "Zeus::RelativeTimerImpl::Wait",
                    "filename": "relative_timer_impl.cpp",
                    "abs_path": "h:\\code\\testmer_impl.cpp",
                    "lineno": 22,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 6,
                    "instruction_addr": "0x99ba0f",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "Zeus::TimerImpl::Run",
                    "sym_addr": "0x99b7c0",
                    "function": "Zeus::TimerImpl::Run",
                    "filename": "timer_impl.cpp",
                    "abs_path": "h:\\code\\cbb\\zeusframework\\code\\foundation\\src\\timer\\impl\\timer_impl.cpp",
                    "lineno": 205,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Invoker_pmf_pointer::_Call",
                    "function": "std::_Invoker_pmf_pointer::_Call",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1340,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::invoke",
                    "function": "std::invoke",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1443,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Invoke_ret",
                    "function": "std::_Invoke_ret",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1475,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Call_binder",
                    "function": "std::_Call_binder",
                    "filename": "functional",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\functional",
                    "lineno": 825,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "function": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "filename": "functional",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\functional",
                    "lineno": 881,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Invoker_functor::_Call",
                    "function": "std::_Invoker_functor::_Call",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1375,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::invoke",
                    "function": "std::invoke",
                    "filename": "type_traits",
                    "abs_path": "c:\\program files (x86)\\microsoft visual studio 14.0\\vc\\include\\type_traits",
                    "lineno": 1443,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "function": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 240,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 7,
                    "instruction_addr": "0x99c464",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "sym_addr": "0x99c3e0",
                    "function": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 247,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 8,
                    "instruction_addr": "0x99c3d5",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "sym_addr": "0x99c3d0",
                    "function": "std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 232,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 9,
                    "instruction_addr": "0x952e39",
                    "package": "C:\\Program Files (x86)\\testService.exe",
                    "symbol": "std::_Pad::_Call_func",
                    "sym_addr": "0x952e30",
                    "function": "std::_Pad::_Call_func",
                    "filename": "xthread",
                    "abs_path": "c:\\program files (x86)\\test\\xthread",
                    "lineno": 209,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 10,
                    "instruction_addr": "0x77cd892e",
                    "package": "C:\\Windows\\System32\\ucrtbase.dll",
                    "symbol": "thread_start<unsigned int (__stdcall*)(void *)>",
                    "sym_addr": "0x77cd88f0",
                    "function": "thread_start<unsigned int (__stdcall*)(void *)>",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 11,
                    "instruction_addr": "0x754b0418",
                    "package": "C:\\Windows\\System32\\kernel32.dll",
                    "symbol": "@BaseThreadInitThunk@12",
                    "sym_addr": "0x754b0400",
                    "function": "@BaseThreadInitThunk@12",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 12,
                    "instruction_addr": "0x77e374ec",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "__RtlUserThreadStart",
                    "sym_addr": "0x77e374be",
                    "function": "__RtlUserThreadStart",
                    "lineno": 0,
                    "trust": "cfi"
                },
                {
                    "status": "symbolicated",
                    "original_index": 13,
                    "instruction_addr": "0x77e374bc",
                    "package": "C:\\Windows\\System32\\ntdll.dll",
                    "symbol": "_RtlUserThreadStart@8",
                    "sym_addr": "0x77e374a2",
                    "function": "_RtlUserThreadStart@8",
                    "lineno": 0,
                    "trust": "cfi"
                }
            ]
        }
    ],
    "modules": [
        {
            "debug_status": "found",
            "unwind_status": "found",
            "features": {
                "has_debug_info": true,
                "has_unwind_info": true,
                "has_symbols": true,
                "has_sources": false
            },
            "arch": "x86",
            "type": "pe",
            "code_id": "61bc71dca3000",
            "code_file": "C:\\Program Files (x86)\\testService.exe",
            "debug_id": "90a07fdf-9b6b-4ca7-95c2-9f25e9cd54d5-1",
            "debug_file": "E:\\wk\\testService.pdb",
            "image_addr": "0x940000",
            "image_size": 667648
        },
        {
            "debug_status": "found",
            "unwind_status": "found",
            "features": {
                "has_debug_info": true,
                "has_unwind_info": true,
                "has_symbols": true,
                "has_sources": false
            },
            "arch": "x86",
            "type": "pe",
            "code_id": "617766af3a000",
            "code_file": "h:\\code\\test_system.cpp",
            "debug_id": "95f13c20-22a6-48dd-9033-8bc9c695a71e-3d",
            "debug_file": "H:\\Code\\testCrash_zsystem.pdb",
            "image_addr": "0xf8f0000",
            "image_size": 237568
        },
        {
            "debug_status": "unused",
            "unwind_status": "unused",
            "features": {
                "has_debug_info": false,
                "has_unwind_info": false,
                "has_symbols": false,
                "has_sources": false
            },
            "arch": "unknown",
            "type": "pe",
            "code_id": "57dfa4812f000",
            "code_file": "C:\\Program Files (x86)\\test\\jsoncpp.dll",
            "debug_id": "ae9097a2-5172-4a36-89a7-b83250ac9b1f-2",
            "debug_file": "D:\\code\\testjsoncpp.pdb",
            "image_addr": "0xfd50000",
            "image_size": 192512
        },
        {
            "debug_status": "found",
            "unwind_status": "found",
            "features": {
                "has_debug_info": true,
                "has_unwind_info": true,
                "has_symbols": true,
                "has_sources": false
            },
            "arch": "x86",
            "type": "pe",
            "code_id": "606d166c25000",
            "code_file": "C:\\Program Files (x86)\\test.dll",
            "debug_id": "22a4dae5-b724-446b-915c-08ab69b46641-6",
            "debug_file": "E:\\test_notify.pdb",
            "image_addr": "0xfde0000",
            "image_size": 151552
        },
        {
            "debug_status": "unused",
            "unwind_status": "unused",
            "features": {
                "has_debug_info": false,
                "has_unwind_info": false,
                "has_symbols": false,
                "has_sources": false
            },
            "arch": "unknown",
            "type": "pe",
            "code_id": "5d10a50147000",
            "code_file": "C:\\testService\\testService.dll",
            "debug_id": "713a5145-4d54-4d9d-b3d8-f0b76ada6d01-1",
            "debug_file": "D:\\aaa\\testSDK.pdb",
            "image_addr": "0xff40000",
            "image_size": 290816
        },
        {
            "debug_status": "unused",
            "unwind_status": "unused",
            "features": {
                "has_debug_info": false,
                "has_unwind_info": false,
                "has_symbols": false,
                "has_sources": false
            },
            "arch": "unknown",
            "type": "pe",
            "code_id": "4b90e9a813c000",
            "code_file": "C:\\Windows\\System32\\msctf.dll",
            "debug_id": "6cb41248-34ea-4128-3762-321ac32bd0a2-1",
            "debug_file": "msctf.pdb",
            "image_addr": "0x75ea0000",
            "image_size": 1294336
        },
        {
            "debug_status": "unused",
            "unwind_status": "unused",
            "features": {
                "has_debug_info": false,
                "has_unwind_info": false,
                "has_symbols": false,
                "has_sources": false
            },
            "arch": "unknown",
            "type": "pe",
            "code_id": "abe9455823000",
            "code_file": "C:\\Windows\\System32\\gdi32.dll",
            "debug_id": "274c409a-e7cd-0ad8-cb01-8f9aa53dbb17-1",
            "debug_file": "wgdi32.pdb",
            "image_addr": "0x75fe0000",
            "image_size": 143360
        },
        {
            "debug_status": "unused",
            "unwind_status": "unused",
            "features": {
                "has_debug_info": false,
                "has_unwind_info": false,
                "has_symbols": false,
                "has_sources": false
            },
            "arch": "unknown",
            "type": "pe",
            "code_id": "6dcb3d2f1c000",
            "code_file": "C:\\Windows\\System32\\profapi.dll",
            "debug_id": "3c5de410-9c0b-948c-8a34-5d8a396cac04-1",
            "debug_file": "profapi.pdb",
            "image_addr": "0x76090000",
            "image_size": 114688
        },
        {
            "debug_status": "unused",
            "unwind_status": "unused",
            "features": {
                "has_debug_info": false,
                "has_unwind_info": false,
                "has_symbols": false,
                "has_sources": false
            },
            "arch": "unknown",
            "type": "pe",
            "code_id": "bd9134a1168000",
            "code_file": "C:\\Windows\\System32\\gdi32full.dll",
            "debug_id": "9236205e-549d-79a7-7f46-54021d6b9723-1",
            "debug_file": "wgdi32full.pdb",
            "image_addr": "0x77ac0000",
            "image_size": 1474560
        },
        {
            "debug_status": "found",
            "unwind_status": "found",
            "features": {
                "has_debug_info": true,
                "has_unwind_info": true,
                "has_symbols": true,
                "has_sources": false
            },
            "arch": "x86",
            "type": "pe",
            "code_id": "51d4b57a123000",
            "code_file": "C:\\Windows\\System32\\ucrtbase.dll",
            "debug_id": "fe4d0f85-65e6-4cb5-8473-b2facf481af4-1",
            "debug_file": "ucrtbase.pdb",
            "image_addr": "0x77c90000",
            "image_size": 1191936
        }
    ]
}
```

代码直接渲染调用：

```java
        String render = RelolverControl.render(resource, templateName, suffix, objStr);
```

得出渲染结果如下：

```xml
崩溃信息：
status:        completed
timestamp:     1970-01-20 07:29:00
os: Windows    Windows 10.0.17763 
cpu_arch:      x86
device_model:  
crashed:       true
crash_reason:  EXCEPTION_INT_DIVIDE_BY_ZERO
assertion:     



线程堆栈：
Thread  32920  crashed
ebp=0x2f3fd80  esp=0x2f3fd3c  edx=0x0  ebx=0xdfed8  esi=0x86f584  ecx=0x0  edi=0xf81ca20  eflags=0x10246  eax=0x3  eip=0xf8f78b8  

0   test_system.cpp    crash_system.cpp:174               Zeus::CrashSystem::TestCrash
1   testService.exe    ds_service.cpp:44                  Test
2   testService.exe    ds_service.cpp:51                  DSService::Main::__l2::<lambda_af106cb3d19e5ac4e87ed1743a9e2573>::operator()
2   testService.exe    type_traits:1375                   std::_Invoker_functor::_Call
2   testService.exe    type_traits:1443                   std::invoke
2   testService.exe    xthread:240                        tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std
2   testService.exe    xthread:247                        tuple<<lambda_af106cb3d19e5ac4e87ed1743a9e2573> >,std::default_delete<std
3   testService.exe    xthread:209                        std::_Pad::_Call_func
4   ucrtbase.dll       wow:0                              thread_start<unsigned int (__stdcall*)(void *)>
5   kernel32.dll       wow:0                              @BaseThreadInitThunk@12
6   ntdll.dll          wow:0                              __RtlUserThreadStart
7   ntdll.dll          wow:0                              _RtlUserThreadStart@8

Thread  14624  
ebp=0x303f7b8  esp=0x303f774  edx=0x0  ebx=0x0  esi=0x0  ecx=0x0  edi=0x10f5bc  eflags=0x206  eax=0x77cd88f0  eip=0x77e433dc  

0   ntdll.dll          wow:0                              NtWaitForAlertByThreadId@8
1   ntdll.dll          wow:0                              RtlSleepConditionVariableSRW
2   KERNELBASE.dll     wow:0                              SleepConditionVariableSRW@16
3   testmer_impl.cpp   winapisupp.cpp:624                 __crtSleepConditionVariableSRW
4   testmer_impl.cpp   primitives.h:187                   Concurrency::details::stl_critical_section_win7::native_handle
5   testmer_impl.cpp   primitives.h:210                   Concurrency::details::stl_condition_variable_win7::wait
6   testmer_impl.cpp   cond.c:74                          do_wait
7   testmer_impl.cpp   cond.c:105                         _Cnd_wait
8   testmer_impl.cpp   xthread:95                         std::_Cnd_waitX
8   testmer_impl.cpp   mutex:565                          std::condition_variable::wait
8   testmer_impl.cpp   mutex:572                          std::condition_variable::wait<<lambda_0a15a138323b69b6cc4b76083e374717> >
9   testmer_impl.cpp   threadpool.hpp:101                 ThreadPool::GetTask
10  testmer_impl.cpp   threadpool.hpp:118                 ThreadPool::Schedual
11  testmer_impl.cpp   type_traits:1340                   std::_Invoker_pmf_pointer::_Call
11  testmer_impl.cpp   type_traits:1443                   std::invoke
11  testmer_impl.cpp   xthread:240                        h:\code\testmer_impl.cpp
11  testmer_impl.cpp   xthread:247                        h:\code\testmer_impl.cpp
12  testmer_impl.cpp   xthread:232                        h:\code\testmer_impl.cpp
13  testmer_impl.cpp   xthread:209                        std::_Pad::_Call_func
14  ucrtbase.dll       wow:0                              thread_start<unsigned int (__stdcall*)(void *)>
15  kernel32.dll       wow:0                              @BaseThreadInitThunk@12
16  ntdll.dll          wow:0                              __RtlUserThreadStart
17  ntdll.dll          wow:0                              _RtlUserThreadStart@8

Thread  37844  
ebp=0x327fd1c  esp=0x327fcd4  edx=0x0  ebx=0xffffffff  esi=0x0  ecx=0x0  edi=0x0  eflags=0x206  eax=0x0  eip=0x77e433dc  

0   ntdll.dll          wow:0                              NtWaitForAlertByThreadId@8
1   ntdll.dll          wow:0                              RtlSleepConditionVariableCS@12
2   KERNELBASE.dll     wow:0                              SleepConditionVariableCS@12
3   testService.exe    event_impl.cpp:110                 Zeus::EventImpl::WaitTimeout
4   testService.exe    chrono:556                         std::chrono::duration_cast
4   testService.exe    event_impl.cpp:29                  Zeus::EventImpl::WaitTimeout
5   testService.exe    relative_timer_impl.cpp:22         Zeus::RelativeTimerImpl::Wait
6   testService.exe    timer_impl.cpp:205                 Zeus::TimerImpl::Run
7   testService.exe    type_traits:1340                   std::_Invoker_pmf_pointer::_Call
7   testService.exe    type_traits:1443                   std::invoke
7   testService.exe    type_traits:1475                   std::_Invoke_ret
7   testService.exe    functional:825                     std::_Call_binder
7   testService.exe    functional:881                     std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde
7   testService.exe    type_traits:1375                   std::_Invoker_functor::_Call
7   testService.exe    type_traits:1443                   std::invoke
7   testService.exe    xthread:240                        std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde
7   testService.exe    xthread:247                        std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde
8   testService.exe    xthread:232                        std::_LaunchPad<std::unique_ptr<std::tuple<std::_Binde
9   testService.exe    xthread:209                        std::_Pad::_Call_func
10  ucrtbase.dll       wow:0                              thread_start<unsigned int (__stdcall*)(void *)>
11  kernel32.dll       wow:0                              @BaseThreadInitThunk@12
12  ntdll.dll          wow:0                              __RtlUserThreadStart
13  ntdll.dll          wow:0                              _RtlUserThreadStart@8




模块信息：
0x940000     0xfa7648     90A07FDF9B6B4CA795C29F25E9CD54D51   testService.exe           testService.pdb           found  (The file was found and successfully processed)
0xf8f0000    0xfb27568    95F13C2022A648DD90338BC9C695A71E3D  test_system.cpp           testCrash_zsystem.pdb     found  (The file was found and successfully processed)
0xfd50000    0xfee2512    AE9097A251724A3689A7B83250AC9B1F2   jsoncpp.dll               testjsoncpp.pdb           unused (The image was not referenced in the stack trace and not further handled)
0xfde0000    0xff31552    22A4DAE5B724446B915C08AB69B466416   test.dll                  test_notify.pdb           found  (The file was found and successfully processed)
0xff40000    0x101d0816   713A51454D544D9DB3D8F0B76ADA6D011   testService.dll           testSDK.pdb               unused (The image was not referenced in the stack trace and not further handled)
0x75ea0000   0x77134336   6CB4124834EA41283762321AC32BD0A21   msctf.dll                 msctf.pdb                 unused (The image was not referenced in the stack trace and not further handled)
0x75fe0000   0x76123360   274C409AE7CD0AD8CB018F9AA53DBB171   gdi32.dll                 wgdi32.pdb                unused (The image was not referenced in the stack trace and not further handled)
0x76090000   0x761a4688   3C5DE4109C0B948C8A345D8A396CAC041   profapi.dll               profapi.pdb               unused (The image was not referenced in the stack trace and not further handled)
0x77ac0000   0x78f34560   9236205E549D79A77F4654021D6B97231   gdi32full.dll             wgdi32full.pdb            unused (The image was not referenced in the stack trace and not further handled)
0x77c90000   0x78e21936   FE4D0F8565E64CB58473B2FACF481AF41   ucrtbase.dll              ucrtbase.pdb              found  (The file was found and successfully processed)
```



## 介绍

relolver，原身是solid，是我在github上面找的一个开源的模板引擎，搞下来基于它做了一些改动和重构，并且为了符合之后应用的一些需求，做了一些功能上的增强

参考ddd方法论对结构进行了重构【没完善】，上层编排调度下层，下层不会依赖上层

![ddd](img/img2.png)

![image-20220225113525364](img/img3.png)

![image-20220225113328791](img/img4.png)

## 然后，随便聊聊

动态网页技术是一项古老的技术，动态页面渲染它的一个流程如下图所示

![img](img/img5.jpg)

1.用户发起请求

2.web应用在初始化启动的时候会把url与对应controller保存路由，路由接收到用户的请求，根据请求的路径去映射中找到对应的处理器

![image-20220225111805812](img/img6.png)

3.在controller这部分的处理逻辑中我们可能会从用户的请求中的一些参数与和数据库产生数据交互，比如这里我们可以根据用户的id去数据库拿到该用户所有的关联信息（拿到需要的数据，我们会把它设定到请求域里面，后面会用到做数据的处理）

4.返回对应的view视图，但是还没有返回到用户的浏览器进行渲染，此时web应用的模板引擎会根据请求域中的数据，对view视图中的一些语法、标记 进行解释、渲染。

5.渲染后的视图返回到用户的浏览器，这时它是一个html文件，浏览器渲染其可视化展示给到用户看。


----------------
v0.0.6 补强函数功能，支持对象迭代，新加定制化函数（用空格补充空位展示起来好看点）
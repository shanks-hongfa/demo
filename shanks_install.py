#!/usr/bin/python
# coding:utf-8
import os
import re
#@auther shanks
# config

PATH_ROUTER_INIT = "app/src/main/java/com/shanks/auctiondemo/RouterInit.java"

# routerInit template path

PATH_ROUTER_INIT_TEMPLATE = "RouterInit.java"

STR_INIT_TEMPLATE = "new com.shanks.{module}.RouterRegister().init();"

Settings_gradle = 'settings.gradle'

App_build_gradle = 'app/build.gradle'
# Global_Dict={package:'com.shanks',routerInit:'RouterInit.java'}


def render_template(fileName, dict):
    template = open(fileName, 'r+').read()
    for key in dict.keys():
        # judge if is regex
        value = dict[key]
        # regex
        if '\\{' in key:
            regex = re.compile(key)
            template = re.sub(regex, value, template)
        else:
            template = template.replace(key, value)
    return template


def wirte_template_2_file(fileName, content):
    out = open(fileName, "w+")
    out.write(content)
    out.close()


def shanks_print(value, round):
    print round * 5, value, round * 5


def generate_module(rootDir, name, size):
    for lists in os.listdir(rootDir):
        path = os.path.join(rootDir, lists)
        if os.path.isdir(path):
            # print name+path[size:]
            print '------ is dir -------'
            dir = (name + path[size:]).replace('module', name)
            os.makedirs(dir)
            print dir
            generate_module(path, name, size)
        else:
            shanks_print(path, '++|')
            if 'DS_Store' in path:
                continue
            print '------ is file -------'
            outputPath = name + path[size:]
            outputPath = outputPath.replace("module", name)
            if '.png' in path or '.jpeg' in path or 'jpg' in path:
                copy_file(name, path, size, outputPath)
            else:
                wirte_template_2_file(
                    outputPath, render_template(path, {'{module}': name}))


def copy_file(name, path, size, outputPath):
    output = open(outputPath, 'wb')
    input = open(path, 'rb')
    output.write(input.read())
    input.close()
    output.close()
    open(name + path[size:], 'wb').write(open(path, 'rb').read())


def init_module(name):
    if os.path.exists(name) == False:
        root_Dir = "shanks/module"
        size = len(root_Dir)
        os.makedirs(name)
        generate_module(root_Dir, name, size)
        os.system('git add ' + name)


def install_module(name):
    init_module(name)
    # find if there is a file named `RouterInit`
    render_router_init(name)
    render_settings_gradle(name)
    render_build_gradle(name)


def render_settings_gradle(name):
    shanks_print('Settings_gradle', '=')
    file = open(Settings_gradle, 'r')
    str = file.readline()
    replace = ", ':" + name + "'"
    while str:
        if 'include' in str:
            if name not in str:
                str = str + replace
                file.close()
                writeF = open(Settings_gradle, 'w')
                writeF.write(str)
                writeF.close()
            break
        str = file.readline()


def render_build_gradle(name):
    shanks_print('App_build_gradle', '*')
    file = open(App_build_gradle, 'r')
    str = file.readline()
    content = str
    while str:
        if 'dependencies' in str:
            check_build_gradle(content, str, file, name)
            break
        str = file.readline()
        content = content + str
    file.close()


def check_build_gradle(content, line, file, name):
    shanks_print('check_build_gradle', '*')
    replace = "compile project(\":{module}\")".replace('{module}', name)
    left = 0
    right = 0
    if '{' in line:
        left += 1
    if '}' in line:
        right += 1
    str = file.readline()
    while str:
        if '{' in str:
            left += 1
        if '}' in str:
            right += 1
        if left == right:
            # end
            if '}' == str.strip():
                str = str.replace('}', '    ' + replace + '\n}')
            else:
                str = str.replace('}', '\n    ' + replace + '\n}')
            content = content + str
            break
        # shanks_print(str,"%%%%")
        if replace in str:
            shanks_print(str, "%%%%")
            file.close()
            return
        content = content + str
        str = file.readline()
    str = file.readline()
    while str:
        content = content + str
        str = file.readline()
    file.close()
    out = open(App_build_gradle, 'w')
    out.write(content)
    out.close()


def render_router_init(name):
    print "routerinit path" + PATH_ROUTER_INIT
    # new
    module = STR_INIT_TEMPLATE.replace("{module}", name)
    print module
    if os.path.exists(PATH_ROUTER_INIT):
        # check if   this module is installed  if not installed ,install it.
        print "exists"
        fo = open(PATH_ROUTER_INIT, "r+")
        str = fo.readline()
        list = []
        while str:
            if str.find("new ") > 0:
                print str
                list.append(str.strip())
                if module in str:
                    print "*******module is already installed*********"
                    # bingo
                    return
            str = fo.readline()
        fo.close()
        print list
        list.append(module)
        print list
    else:  # if not make it
        print "make it"
    # make it
    for index in range(len(list)):
        print list[index]
        list[index] = "\n       " + list[index]
    wirte_template_2_file(PATH_ROUTER_INIT, render_template(
        'shanks/' + PATH_ROUTER_INIT_TEMPLATE, {'\s+\\{body\\}': "".join(list)}))

    ###

    # read file and  list all the modules


def remove_module_from_file(name, path, replace):
    # rm settings
    file = open(path, 'r')
    str = file.readline()
    content = ''
    # replace=',\s*\':'+name+'\''
    regex = re.compile(replace)
    flag = False
    while str:
        if name in str:
            shanks_print(str, '*')
            strTemp = str.strip()
            str = re.sub(regex, '', strTemp)
            print str
            print strTemp
            if strTemp != str:
                flag = True
        content = content + str
        str = file.readline()
    file.close()
    if flag:
        file = open(path, 'w')
        file.write(content)
        file.close()
        # print content


def uninstall_module(name, force=False):
    replace = ',\s*\':' + name + '\''
    remove_module_from_file(name, Settings_gradle, replace)
    replace = 'compile\s+project\(":' + name + '"\)'
    print replace
    remove_module_from_file(name, App_build_gradle, replace)
    replace = 'new\s+com.shanks.' + name + '.RouterRegister\(\).init\(\);'
    remove_module_from_file(name, PATH_ROUTER_INIT, replace)
    if force:
        remove_force_module(name)
        os.rmdir(name)

    ##


def remove_force_module(name):
    for lists in os.listdir(name):
        path = os.path.join(name, lists)
        if os.path.isdir(path):
            print path
            remove_force_module(path)
            os.rmdir(path)
        else:
            print path
            os.remove(path)

# init  something


def init():
    shanks_print('init start', "%")
    file = open('shanks/app.properties', 'r')
    line = file.readline()
    mapResult = {}
    while line:
        result = line.strip().split('=', 1)
        if(len(result) == 2):
            print result
            mapResult[result[0].strip()] = result[1].strip()
        line = file.readline()
    # com/shanks/auctiondemo
    PackagePath = mapResult['Package'].replace('.', '/')
    global PATH_ROUTER_INIT
    PATH_ROUTER_INIT = PATH_ROUTER_INIT.replace(
        'com/shanks/auctiondemo', PackagePath + '/' + mapResult['MainModule'])
    print PATH_ROUTER_INIT
    global STR_INIT_TEMPLATE
    print STR_INIT_TEMPLATE
    STR_INIT_TEMPLATE = STR_INIT_TEMPLATE.replace(
        'com.shanks', mapResult['Package'])
    file.close()
init()
# install_module("mylibraryssslll")
# install_module("mylibrary")
# install_module("map")
   # compile project(":mylibrary")
   #  compile project(":reactnative")
   #  compile project(":shanks_hello")
# uninstall_module("mylibrary")
# uninstall_module("reactnative")
uninstall_module("shanks_hello")
uninstall_module("live")
install_module("base")
# must run

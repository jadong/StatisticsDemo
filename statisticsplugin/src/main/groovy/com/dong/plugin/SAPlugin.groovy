package com.dong.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
<<<<<<< HEAD
import com.dong.visit.SaFlagMethodClassVisitor
=======
import com.dong.visit.ClassVisitorAdapter
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils

public class SAPlugin extends Transform implements Plugin<Project> {


    @Override
    void apply(Project project) {

        System.out.println("---- SAPlugin execute --------")
        /*
         //task耗时监听
         project.gradle.addListener(new TimeListener())
        */

        //遍历class文件和jar文件，在这里可以进行class文件asm文件替换
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(this)
    }

    @Override
    String getName() {
        return "SAPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println '//===============asm visit start===============//'
        //遍历inputs里的TransformInput
        inputs.each { TransformInput input ->
            //遍历input里边的DirectoryInput
            input.directoryInputs.each {
                DirectoryInput directoryInput ->
                    //是否是目录
                    if (directoryInput.file.isDirectory()) {
                        //遍历目录
                        directoryInput.file.eachFileRecurse {
                            File file ->
                                def name = file.name
<<<<<<< HEAD
                                //这里进行我们的处理 TODO
=======
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
                                if (name.endsWith(".class") && !name.startsWith("R\$") &&
                                        "R.class" != name && "BuildConfig.class" != name) {
                                    ClassReader classReader = new ClassReader(file.bytes)
                                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
<<<<<<< HEAD
                                    ClassVisitor cv = new SaFlagMethodClassVisitor(classWriter)
=======
                                    ClassVisitor cv = new ClassVisitorAdapter(classWriter)
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
                                    classReader.accept(cv, ClassReader.EXPAND_FRAMES)
                                    byte[] code = classWriter.toByteArray()
                                    FileOutputStream fos = new FileOutputStream(
                                            file.parentFile.absolutePath + File.separator + name)
                                    fos.write(code)
                                    fos.close()
<<<<<<< HEAD
                                }
                                println '//SAPlugin find file:' + file.getAbsolutePath()
=======
                                    println '//SAPlugin processed file:' + file.getAbsolutePath()
                                }
>>>>>>> 638a779bfaa050f61283e272dea2bcd76e9750e5
                        }
                    }
                    //处理完输入文件之后，要把输出给下一个任务
                    def dest = outputProvider.getContentLocation(directoryInput.name,
                            directoryInput.contentTypes, directoryInput.scopes,
                            Format.DIRECTORY)
                    FileUtils.copyDirectory(directoryInput.file, dest)
            }


            input.jarInputs.each { JarInput jarInput ->
                /**
                 * 重名名输出文件,因为可能同名,会覆盖
                 */
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                println '//SAPlugin find Jar:' + jarInput.getFile().getAbsolutePath()

                //处理jar进行字节码注入处理 TODO

                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                FileUtils.copyFile(jarInput.file, dest)
            }
        }
        println '//===============asm visit end===============//'

    }

}
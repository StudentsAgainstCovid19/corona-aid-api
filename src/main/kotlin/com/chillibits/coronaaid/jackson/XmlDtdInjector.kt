package com.chillibits.coronaaid.jackson

import com.chillibits.coronaaid.shared.XmlDtdUrl
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.lang.reflect.Method
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class XmlDtdInjector: HandlerInterceptorAdapter() {

    private val reflectionCache: MutableMap<Method, String?> = mutableMapOf()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if(handler is HandlerMethod && request.getHeader("Accept").contains("application/xml")) {
            handleMethod(response, handler)
        }

        println(response.javaClass.name)

        return super.preHandle(request, response, handler)
    }

    private fun handleMethod(response: HttpServletResponse,handler: HandlerMethod): Int {
        if(reflectionCache.containsKey(handler.method)) {
            return writeToServlet(response, reflectionCache[handler.method])
        }

        val url = readDtdAnnotation(handler.method)
        var combined = XML_HEADER

        if(!url.isNullOrEmpty()) {
            combined += XML_DTD_TEMPLATE.replace("%DTD_URL%", url)
        }

        reflectionCache.put(handler.method, combined)
        return writeToServlet(response, combined)
    }

    private fun readDtdAnnotation(method: Method): String? {
        for(annotation in method.declaredAnnotations) {
            if(annotation.annotationClass.equals(XmlDtdUrl::class)) {
                return (annotation as XmlDtdUrl).url
            }
        }

        return null
    }

    private fun writeToServlet(response : HttpServletResponse, data: String?): Int {
        if(data != null) {
            val byteArray = data.toByteArray()
            println(response.outputStream.javaClass.name)
            response.outputStream.write(byteArray)
            response.contentType = "application/xml"
            response.outputStream.flush()

            return byteArray.size
        }

        return 0
    }

    companion object {
        const val XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        const val XML_DTD_TEMPLATE = "<!DOCTYPE Set SYSTEM \"%DTD_URL%\">"
    }

}